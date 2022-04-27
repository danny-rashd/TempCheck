# %%
import tensorflow as tf
import os
import pandas as pd
import numpy as np

# %%
df = pd.read_csv(r"dataset/temp_FINAL.csv",parse_dates=['Datetime'])
df

# %%
df.index = pd.to_datetime(df['Datetime'], infer_datetime_format=True)
df[:26]

# %%
temp = df['Temperature']
temp.plot()                     

# %%
def df_to_X_y(df, window_size=5):
    df_as_np  = df.to_numpy()
    X= []
    y= []
    for i in range(len(df_as_np)- window_size):
        row = [[a] for a in df_as_np[i:i+5]]
        X.append(row)
        label = df_as_np[i+5]
        y.append(label)
    return np.array(X), np.array(y)

# %%
WINDOW_SIZE = 5
X, y = df_to_X_y(temp, WINDOW_SIZE)
X.shape, y.shape

# %%
#Split the data to train and test set
X_train, y_train = X[:20000], y[:20000]
X_val, y_val = X[20000:24000], y[20000:24000]
X_test, y_test = X[24000:], y[24000:]
X_train.shape, y_train.shape, X_val.shape, y_val.shape, X_test.shape, y_test.shape

# %%
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import *
from tensorflow.keras.callbacks import ModelCheckpoint
from tensorflow.keras.losses import MeanSquaredError
from tensorflow.keras.metrics import RootMeanSquaredError
from tensorflow.keras.optimizers import Adam

model1 = Sequential()
model1.add(InputLayer((5,1)))
model1.add(LSTM((64)))
model1.add(Dense(8,'relu'))
model1.add(Dense(1,'linear'))


model1.summary()

# %%
cp = ModelCheckpoint('model1/',save_best_only=True)


model1.compile(loss='mean_absolute_error', optimizer=Adam(learning_rate=0.0001), metrics=[RootMeanSquaredError()])


# %%
model1.fit(X_train,y_train, validation_data=(X_val,y_val),epochs=10, callbacks=[cp])                                            

# %%
model1.save('nooutlier_keras.h5')

# %%
from tensorflow.keras.models import load_model
model1 = load_model('model1/')

# %%
train_predictions = model1.predict(X_train).flatten()
train_results = pd.DataFrame(data={'Train Predictions':train_predictions, 'Actuals': y_train})
train_results

# %%
import matplotlib.pyplot as plt
plt.plot(train_results['Train Predictions'][:100])
plt.plot(train_results['Actuals'][:100])

# %%
val_predictions = model1.predict(X_val).flatten()
val_results = pd.DataFrame(data = {'Val predictions': val_predictions, 'Actuals': y_val})
val_results

# %%
plt.plot(val_results['Val predictions'][:100])
plt.plot(val_results['Actuals'][:100])

# %%
test_predictions = model1.predict(X_test).flatten()
test_results = pd.DataFrame(data={'Test Predictions': test_predictions, 'Actuals':y_test})
test_results

# %%
X_test.shape , test_predictions.shape

# %%
test_results[:100]

# %%
plt.plot(test_results['Test Predictions'][0:100])
plt.plot(test_results['Actuals'][0:100])


