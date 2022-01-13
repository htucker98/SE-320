# example of training to the test set for the housing dataset
import re
from typing import List, Union

import hypothesis
import pandas as pd
import pytest
import unittest
from hypothesis import given
from hypothesis import database
from hypothesis.strategies import builds, just, one_of
from nltk.corpus import stopwords

import dalex as dx
import numpy as np

import matplotlib

from sklearn.compose import ColumnTransformer
from sklearn.linear_model import LogisticRegression
from sklearn.naive_bayes import MultinomialNB
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import OneHotEncoder
import hypothesis.strategies as st
import random
from sklearn import metrics
from sklearn.feature_extraction.text import CountVectorizer, TfidfVectorizer, TfidfTransformer
from sklearn.tree import DecisionTreeClassifier
from sklearn import tree
from mlinsights.plotting import *


def generateRows():
    data = dx.datasets.load_german()
    predictions = []
    for i in range(0, 500):
        prediction = []
        prediction.append(random.choice(['male', 'female']))
        prediction.append(random.choice([1, 2, 3]))
        prediction.append(random.choice(['own', 'free', 'rent']))
        prediction.append(random.choice(['not_known', 'little', 'quite rich', 'rich', 'moderate']))
        prediction.append(random.choice(['not_known', 'little', 'quite rich', 'rich', 'moderate']))
        prediction.append(random.choice(data.credit_amount.unique().reshape(-1).tolist()))
        prediction.append(random.choice(data.duration.unique().reshape(-1).tolist()))
        prediction.append(random.choice(['radio/TV', 'education', 'car', 'furniture/equipment', 'business']))
        prediction.append(random.choice(data.age.unique().reshape(-1).tolist()))
        predictions.append(prediction)
    return pd.DataFrame(predictions,
                        columns=['sex', 'job', 'housing', 'saving_accounts', 'checking_account', 'credit_amount',
                                 'duration', 'purpose', 'age'])


def generateUniqueRows():
    data = dx.datasets.load_german()
    predictions = []
    credit = data.credit_amount.unique().reshape(-1).tolist()
    duration = (data.duration.unique().reshape(-1).tolist())
    age = (data.age.unique().reshape(-1).tolist())
    for f in range(min(credit), max(credit) + 1):
        for i in range(min(age), max(age) + 1):
            for g in range(min(duration), max(duration) + 1):
                for a in range(0, 2):
                    for b in range(0, 3):
                        for c in range(0, 3):
                            for d in range(0, 5):
                                for e in range(0, 5):
                                    for h in range(0, 5):
                                        prediction = []
                                        prediction.append((['male', 'female'])[a])
                                        prediction.append(([1, 2, 3])[b])
                                        prediction.append((['own', 'free', 'rent'])[c])
                                        prediction.append(
                                            (['not_known', 'little', 'quite rich', 'rich', 'moderate'])[d])
                                        prediction.append(
                                            (['not_known', 'little', 'quite rich', 'rich', 'moderate'])[e])
                                        prediction.append(f)
                                        prediction.append(g)
                                        prediction.append((['radio/TV', 'education', 'car', 'furniture/equipment', 'business'])[h])
                                        prediction.append(i)
                                        predictions.append(prediction)
    return predictions


@given(sex=one_of(just('male'), just('female')), job=st.integers(min_value=0, max_value=2),
       housing=one_of(just('own'), just('free'), just('rent')),
       saving=one_of(just('not_known'), just('little'), just('quite_rich'), just('rich'), just('moderate')),
       checking=one_of(just('not_known'), just('little'), just('quite_rich'), just('rich'), just('moderate')),
       credit=st.integers(min_value=250, max_value=18424), duration=st.integers(min_value=4, max_value=72),
       purpose=one_of(just('radio/TV'), just('education'), just('car'), just('furniture/equipment'),
                      just('business')), age=st.integers(min_value=19, max_value=75))
def addRow(sex, job, housing, saving, checking, credit, duration, purpose, age):
    prediction = []
    prediction.append(sex)
    prediction.append(job)
    prediction.append(housing)
    prediction.append(saving)
    prediction.append(checking)
    prediction.append(credit)
    prediction.append(duration)
    prediction.append(purpose)
    prediction.append(age)
    return prediction


if __name__ == "__main__":
    data = dx.datasets.load_german()

    # risk is the target
    X = data.drop(columns='risk')
    y = data.risk

    categorical_features = ['sex', 'job', 'housing', 'saving_accounts', "checking_account", 'purpose']
    categorical_transformer = Pipeline(steps=[
        ('onehot', OneHotEncoder(handle_unknown='ignore'))
    ])
    preprocessor = ColumnTransformer(transformers=[
        ('cat', categorical_transformer, categorical_features)
    ])
    clf = Pipeline(steps=[
        ('preprocessor', preprocessor),
        ('classifier', LogisticRegression())
    ])
    model = clf.fit(X, y)
    predictions = generateUniqueRows()
