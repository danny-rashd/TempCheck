{
 "cells": [
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "5dfee631",
   "metadata": {},
   "outputs": [],
   "source": [
    "import pandas as pd\n",
    "import numpy as np\n",
    "import time\n",
    "from datetime import datetime"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "edcb0383",
   "metadata": {},
   "outputs": [],
   "source": [
    "pd.set_option('display.float_format', str)\n",
    "df = pd.read_csv(r\"temperature.csv\",names=['A', 'B', 'Epoch','Temperature'])"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "17a89af8",
   "metadata": {},
   "outputs": [],
   "source": [
    "df.head(20)"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "985ff2fa",
   "metadata": {},
   "outputs": [],
   "source": [
    "df['Datetime'] = pd.to_datetime(df['Epoch'],unit='ms')"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "7fa80f14",
   "metadata": {},
   "outputs": [],
   "source": [
    "df['Datetime']"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "a7099462",
   "metadata": {},
   "outputs": [],
   "source": [
    "## convert utc to local time\n",
    "df['Datetime'] = df['Datetime'].dt.tz_localize('UTC').dt.tz_convert('Asia/Kuala_Lumpur')\n",
    "df['Datetime']"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "7be0f373",
   "metadata": {},
   "outputs": [],
   "source": [
    "df.head()"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "cdea61e6",
   "metadata": {},
   "outputs": [],
   "source": [
    "df.shape"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "127e8d78",
   "metadata": {},
   "outputs": [],
   "source": [
    "df.to_csv(r'temp_cleaned_v1.csv', index = False)"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "ee88a220",
   "metadata": {},
   "outputs": [],
   "source": [
    "df = df[df['Temperature'].notna()]"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "d699e577",
   "metadata": {},
   "outputs": [],
   "source": [
    "df.shape"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "id": "e84d1590",
   "metadata": {},
   "outputs": [],
   "source": [
    "df.to_csv(r'temp_cleaned_v2_notnull.csv', index = False)"
   ]
  }
 ],
 "metadata": {
  "kernelspec": {
   "display_name": "Python 3 (ipykernel)",
   "language": "python",
   "name": "python3"
  },
  "language_info": {
   "codemirror_mode": {
    "name": "ipython",
    "version": 3
   },
   "file_extension": ".py",
   "mimetype": "text/x-python",
   "name": "python",
   "nbconvert_exporter": "python",
   "pygments_lexer": "ipython3",
   "version": "3.9.7"
  }
 },
 "nbformat": 4,
 "nbformat_minor": 5
}
