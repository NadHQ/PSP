from flask import Flask, request
import json
from flask_restful import Api, Resource
import keras
import numpy as np
from keras.datasets import boston_housing

app = Flask(__name__)
api = Api(app)




class HelloWorld(Resource):
    def put(self):
        my_json = request.get_data().decode('utf8')
        data_raw = json.loads(my_json)
        data = data_raw.get("data")
        data = data.replace('[', '')
        data = data.replace(']', '')
        data = data.replace(']', '')
        print(data)
        data = data.split(',')
        print(data)
        data = np.asarray(data, dtype=np.float64)
        (train, train_target), (test, test_target) = boston_housing.load_data()
        mean = train.mean(axis=0)
        std = train.std(axis=0)
        model = keras.models.load_model('model.h5')
        data -= mean
        data /= std
        data = data.reshape(1,13)
        print(data)
        prediction = model.predict(data)
        prediction = prediction.astype(float)
        prediction = prediction[0][0]
        return {"name": prediction}


api.add_resource(HelloWorld, "/helloworld")
if __name__ == "__main__":
    app.run(debug=True)

#
# # [3.74511057e+00 1.14801980e+01 1.11044307e+01 6.18811881e-02
# #  5.57355941e-01 6.26708168e+00 6.90106436e+01 3.74027079e+00
# #  9.44059406e+00 4.05898515e+02 1.84759901e+01 3.54783168e+02
# #  1.27408168e+01]   MEAN
#
#
# # [9.22929073e+00 2.37382770e+01 6.80287253e+00 2.40939633e-01
# #  1.17147847e-01 7.08908627e-01 2.79060634e+01 2.02770050e+00
# #  8.68758849e+00 1.66168506e+02 2.19765689e+00 9.39946015e+01
# #  7.24556085e+00] STD
