from distutils.cmd import Command
from tkinter import N
from tokenize import Imagnumber
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import time


db = "PDF Scraper\\data"
l = []
with open(db, 'r') as f:
    l = [line.split("'")[1] for line in f]

for i in range(26,83):
    for j in range(3):
        link = f"https://raw.githubusercontent.com/satmxd/VVIM-App/main/data/picdb/{l[i]}-{j+1}.png"
        img = mpimg.imread(link)
        imgplot = plt.imshow(img)
        print(link)
        plt.show(block = False)
        plt.pause(0.4)
        plt.close('all')