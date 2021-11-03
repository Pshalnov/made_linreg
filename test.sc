import breeze.io.{CSVReader,CSVWriter}

import breeze.linalg._
import java.io._

def readCSVFile(filePath: String) : DenseMatrix[Double] = {
  val dataFrame: DenseMatrix[Double] = csvread(new File(filePath), separator = ';')
  return dataFrame
}

val df = readCSVFile("/Users/pavel/IdeaProjects/made_linreg/train_scala_project.csv")
val X_train = df(::, 0 to -2)
val y_train = df(::, -1)
