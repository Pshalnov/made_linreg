package test

import breeze.io.{CSVReader,CSVWriter}
import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.stats.mean
import breeze.numerics.pow


import breeze.linalg._
import java.io._



object Main {
  class LinearRegression {

    var k : DenseVector[Double] = DenseVector[Double]()

    def fit(X_train: DenseMatrix[Double], y_train: DenseVector[Double]) : Unit = {
      DenseMatrix.horzcat(X_train, DenseMatrix.ones[Double](y_train.length, 1))
      k = (pinv(X_train.t * X_train) * X_train.t * y_train)

    }
    def predict(X_test: DenseMatrix[Double]) : DenseVector[Double] = {
      DenseMatrix.horzcat(X_test, DenseMatrix.ones[Double](X_test.rows, 1))
      return (X_test * k)
    }
    def readCSVFile(filePath: String) : DenseMatrix[Double] = {
      val dataFrame: DenseMatrix[Double] = csvread(new File(filePath), separator = ';')
      return dataFrame
    }
  }

  def mse(pred: DenseVector[Double], y_true: DenseVector[Double]) : Double = {
    return mean(pow(pred - y_true, 2))
  }



  def main (args : Array[String]) : Unit = {
    var model = new LinearRegression()
    val train = model.readCSVFile("/Users/pavel/IdeaProjects/made_linreg/train_scala_project.csv")
    val test = model.readCSVFile("/Users/pavel/IdeaProjects/made_linreg/test_scala_project.csv")

    val X_train = train(::, 0 to -2)
    val X_test = test(::, 0 to -2)
    val y_train = train(::, -1)
    val y_test = test(::, -1)

    model.fit(X_train, y_train)
    var predict = (model.predict(X_test))
    val metrics = mse(predict, y_test)
    println(f"mse = $metrics")


    csvwrite(new File("./result.csv"), predict.asDenseMatrix, ';')
  }
}