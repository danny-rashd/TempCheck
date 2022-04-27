# %%
import pandas as pd
import numpy as np
import time
from datetime import datetime

# %%
pd.set_option('display.float_format', str)
df = pd.read_csv(r"temperature.csv",names=['A', 'B', 'Epoch','Temperature'])

# %%
df.head(20)

# %%
df['Datetime'] = pd.to_datetime(df['Epoch'],unit='ms')

# %%
df['Datetime']

# %%
## convert utc to local time
df['Datetime'] = df['Datetime'].dt.tz_localize('UTC').dt.tz_convert('Asia/Kuala_Lumpur')
df['Datetime']

# %%
df.head()

# %%
df.shape

# %%
df.to_csv(r'temp_cleaned_v1.csv', index = False)

# %%
df = df[df['Temperature'].notna()]

# %%
df.shape

# %%
df.to_csv(r'temp_cleaned_v2_notnull.csv', index = False)


