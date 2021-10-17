from random import randint as rd
import subprocess as sp
import os, sys

dispo = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 25, 50, 75, 100]

def getRandomOut()-> str:
    return str(rd(101, 999))

def getRandomIn() -> str:
    return str(dispo[rd(0, len(dispo)-1)])+" "

folders = [p for p in os.listdir() if os.path.isdir(p)]

if (len(sys.argv) < 2):
    NB_TESTS = 1
else: NB_TESTS = int(sys.argv[1])
for i in range(NB_TESTS):
    args = ""
    for n in range(6):
        args += getRandomIn()
    args += getRandomOut()
    for name in folders:
        print()
        cmd = "C:/Users/FurWaz/Desktop/jdk-15/bin/java -cp \"./"+name+";./\" CompteEstBon "+args
        print("Testing command ["+cmd+"]")
        print("<== TESTING "+name+" ==>")
        out = sp.run(cmd, capture_output=True, shell=True)
        print(out.stdout.decode(), end="")
        print(out.stderr.decode(), end="")