"""
Given an array of distinct integers and a sum value.
Find the count of triplets with a sum smaller than the given sum value.
"""
import datetime
import time
from PyQt5.QtCore import Qt

from CountTripletsN3 import CountTripletsN3
# from CountTripletsN2 import CountTripletsN2
import sys
from PyQt5.QtWidgets import QApplication, QPushButton, QVBoxLayout, QDialog, QLabel


class MainWindow(QDialog):
    def __init__(self):
        super().__init__()
        self.setWindowFlags(Qt.Window)

        self.setWindowTitle("Count Triplets")
        button1 = QPushButton()
        button1.setText("Count")
        button1.clicked.connect(self.button_clicked)

        self.label = QLabel("Nr: 0")

        layout = QVBoxLayout()
        layout.addWidget(self.label)
        layout.addWidget(button1)
        self.setGeometry(50, 50, 320, 200)
        self.setLayout(layout)

    def button_clicked(self):
        now = datetime.datetime.now()
        counter = CountTripletsN3("1Kint_2.txt")
        val = counter.countTriplets(0)
        later = datetime.datetime.now()
        difference = (later - now).total_seconds()
        self.label.setText("Nr: "+str(val)+" \nRun time: "+str(difference))


app = QApplication(sys.argv)
app.setStyleSheet("QLabel{font-size: 18pt;}")
window = MainWindow()
window.show()
app.exec()
