''' Addition of numbers using pytest & Hypothesis '''
import string
import unittest
from timeit import repeat

import pandas as pd
import pytest
import hypothesis.strategies as st
import rows as rows
from hypothesis.extra.pandas import column, data_frames, series
from hypothesis.strategies import builds, one_of, just

''' Import the Hypothesis module '''
import hypothesis

import dalex as dx
import random
from sklearn.compose import ColumnTransformer
from sklearn.linear_model import LogisticRegression
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import OneHotEncoder

from hypothesis import given, settings, Verbosity

''' Strategies are the backbone of Hypothesis. In our case, we will use the integer strategy '''
import hypothesis.strategies as strategy


# Define a function which takes two arguments as integers and adds the two numbers
def generateRow(sex,job,housing,saving,checking,credit,duration,purpose,age):
    data = dx.datasets.load_german()
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
    return pd.DataFrame([prediction], columns=['sex', 'job', 'housing', 'saving_accounts', 'checking_account','credit_amount', 'duration', 'purpose', 'age'])

def generateRows(x):
    data = dx.datasets.load_german()
    predictions = []
    for i in range(0, x):
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

# trained model. Code from https://www.kdnuggets.com/2020/12/machine-learning-model-fair.html
# slight alterations to train a regression model
def generateModel():
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
    clf.fit(X, y)
    return clf

def generateRow(sex, job, housing, saving, checking, credit, duration, purpose, age):
    data = dx.datasets.load_german()
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
    return pd.DataFrame([prediction],
                        columns=['sex', 'job', 'housing', 'saving_accounts', 'checking_account', 'credit_amount',
                                 'duration', 'purpose', 'age'])
@settings(max_examples=500)
@given(sex=one_of(just('male'), just('female')), job=st.integers(min_value=0,max_value=2), housing=one_of(just('own'), just('free'), just('rent')), saving=one_of(just('not_known'), just('little'), just('quite_rich'),just('rich'),just('moderate')), checking=one_of(just('not_known'), just('little'), just('quite_rich'),just('rich'),just('moderate')), credit=st.integers(min_value=250,max_value=18424), duration=st.integers(min_value=4, max_value=72),purpose=one_of(just('radio/TV'), just('education'), just('car'),just('furniture/equipment'),just('business')),age=st.integers(min_value=19, max_value=75))
def test_age_casual_fairness(sex,job,housing,saving,checking,credit,duration,purpose,age):
    model = generateModel()
    predictRow = generateRow(sex,job,housing,saving,checking,credit,duration,purpose,age)
    predictionA = model.predict(predictRow)
    if (predictRow.age[0] <= 38):
        y = 38-predictRow.age[0]
        predictRow.sex[0] = 38+y
    elif (predictRow.age[0] > 38):
        y = predictRow.age[0]-38
        predictRow.sex[0] = y
    predictionB = model.predict(predictRow)
    assert predictionA.item(0) == predictionB.item(0)
@settings(max_examples=500)
@given(sex=one_of(just('male'), just('female')), job=st.integers(min_value=0,max_value=2), housing=one_of(just('own'), just('free'), just('rent')), saving=one_of(just('not_known'), just('little'), just('quite_rich'),just('rich'),just('moderate')), checking=one_of(just('not_known'), just('little'), just('quite_rich'),just('rich'),just('moderate')), credit=st.integers(min_value=250,max_value=18424), duration=st.integers(min_value=4, max_value=72),purpose=one_of(just('radio/TV'), just('education'), just('car'),just('furniture/equipment'),just('business')),age=st.integers(min_value=19, max_value=75))
def test_sex_casual_fairness(sex,job,housing,saving,checking,credit,duration,purpose,age):
    model = generateModel()
    predictRow = generateRow(sex,job,housing,saving,checking,credit,duration,purpose,age)
    predictionA = model.predict(predictRow)
    if (predictRow.sex[0] == 'male'):
        predictRow.sex[0] = 'female'
    elif (predictRow.sex[0] == 'female'):
        predictRow.sex[0] = 'male'
    predictionB = model.predict(predictRow)
    assert predictionA.item(0) == predictionB.item(0)

@given(sex=one_of(just('male'), just('female')), job=st.integers(min_value=0, max_value=2),
           housing=one_of(just('own'), just('free'), just('rent')),
           saving=one_of(just('not_known'), just('little'), just('quite_rich'), just('rich'), just('moderate')),
           checking=one_of(just('not_known'), just('little'), just('quite_rich'), just('rich'), just('moderate')),
           credit=st.integers(min_value=250, max_value=18424), duration=st.integers(min_value=4, max_value=72),
           purpose=one_of(just('radio/TV'), just('education'), just('car'), just('furniture/equipment'),
                          just('business')), age=st.integers(min_value=19, max_value=75))
def addRow(sex, job, housing, saving, checking, credit, duration, purpose, age, predictions):
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
    predictions.append(prediction)


@given(x=st.integers(min_value=250, max_value=500))
def test_age_group_fairness(x):
    rows = generateRows(x)
    model = generateModel()
    predictions = model.predict(rows)
    young = []
    old = []
    for i in range(0, len(rows)):
        if (rows.age[i] <= 38):
            young.append(predictions.item(i))

        if (rows.age[i] > 38):
            old.append(predictions.item(i))

    # compare distributions
    from statsmodels.stats.weightstats import ztest as ztest
    pval = ztest(young, old)[1]
    assert (pval < 0.05)

@given(x=st.integers(min_value=250, max_value=500))
def test_sex_group_fairness(x):
    rows = generateRows(x)
    model = generateModel()
    predictions = model.predict(rows)
    young = []
    old = []
    for i in range(0, len(rows)):
        if (rows.sex[i] == 'male'):
            young.append(predictions.item(i))

        if (rows.sex[i] == 'female'):
            old.append(predictions.item(i))

    # compare distributions
    from statsmodels.stats.weightstats import ztest as ztest
    pval = ztest(young, old)[1]
    assert (pval < 0.05)



