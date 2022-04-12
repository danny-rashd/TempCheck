package com.example.temperaturemodel;

import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.writable.Writable;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.InvocationType;
import org.deeplearning4j.optimize.listeners.EvaluativeListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.common.io.ClassPathResource;
import org.datavec.local.transforms.LocalTransformExecutor;
import org.nd4j.evaluation.regression.RegressionEvaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.ViewIterator;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.awt.windows.WFontConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemperaturePrediction {

    private static Logger log = LoggerFactory.getLogger(TemperaturePrediction.class);

    private static final int nEpoch = 100;
    private static final int batchSize = 256;
    private static final int seed = 256;
    private static final int learningRate = 123;

    public static void main(String[] args) throws IOException, InterruptedException {

        //Prepare the data
        File file = new ClassPathResource("temperature_v2.csv").getFile();
        CSVRecordReader csvReader = new CSVRecordReader(1,',');
        csvReader.initialize(new FileSplit(file));

        // temperature.csv -> A,B,EpochTime,Temperature,Datetime
        Schema inputSchema = new Schema.Builder()
                .addColumnString("A")
                .addColumnInteger("B")
                .addColumnsDouble("EpochTime","Temperature")
                .addColumnString("Datetime")
                .build();

        TransformProcess tp = new TransformProcess.Builder(inputSchema)
                .removeColumns("A", "B","Datetime")
                /*
Insert feature names in schema based on csv columns
 */
                .build();


        List<List<Writable>> data = new ArrayList<>();
        while (csvReader.hasNext()) {
            data.add(csvReader.next());
        }

        List<List<Writable>> transformedData = LocalTransformExecutor.execute(data, tp);

        System.out.println("=======Initial Schema=========\n" + tp.getInitialSchema());
        System.out.println("=======Final Schema=========\n" + tp.getFinalSchema());
        System.out.println("Size before transform: " + data.size() +
                "\nColumns before transform: " + tp.getInitialSchema().numColumns());
        System.out.println("Size after transform: " + transformedData.size() +
                "\nColumns after transform: " + tp.getFinalSchema().numColumns());

        for (int i = 0; i < transformedData.size(); i++) {
            System.out.println(transformedData.get(i));
        }

        //Split data
        CollectionRecordReader crr = new CollectionRecordReader(transformedData);

        DataSetIterator dataSetIterator =
                new RecordReaderDataSetIterator(crr, transformedData.size(), 1, 1, true);

        DataSet allData = dataSetIterator.next();
        allData.shuffle();

        //80:20 train:test split
        SplitTestAndTrain stt = allData.splitTestAndTrain(0.8);

        DataSet trainSet = stt.getTrain();
        DataSet testSet = stt.getTest();


        DataNormalization scaler = new NormalizerMinMaxScaler(0, 1);
        scaler.fitLabel(true);
        scaler.fit(trainSet);
        scaler.transform(trainSet);
        scaler.transform(testSet);

        ViewIterator trainIter = new ViewIterator(trainSet, batchSize);
        ViewIterator testIter = new ViewIterator(testSet, batchSize);

        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .activation(Activation.RELU)
                .updater(new Adam(learningRate))
                .list()
                .layer(new DenseLayer.Builder()
                        .nIn(trainIter.inputColumns())
                        .nOut(50)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .nOut(1)
                        .activation(Activation.IDENTITY)
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(config);
        model.init();

        log.info("Training...");
        model.setListeners(new ScoreIterationListener(100), new EvaluativeListener(testIter, 1, InvocationType.EPOCH_END));

        log.info("\nSaving the trained model: ");
        ModelSerializer.writeModel(model, "temp_prediction.net", true);

        log.info("\nRegression Evaluation : ");
        RegressionEvaluation trainEval = model.evaluateRegression(trainIter);
        System.out.println(trainEval);
        RegressionEvaluation testEval = model.evaluateRegression(testIter);
        System.out.println(testEval);
    }

}
