package com.example.temperaturemodel;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;
import java.io.IOException;

public class TemperatureInference {

    MultiLayerNetwork config = MultiLayerNetwork.load(new File(""), true);

    public TemperatureInference() throws IOException {
    }
}
