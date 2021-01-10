from tkinter import *
from tkinter import filedialog

import requests


def readfromdb():
    resp = requests.get('http://localhost:8080/myRESTwsWeb/rest/readExams')
    if resp.status_code != 200:
        raise print('GET /tasks/ {}'.format(resp.status_code))
    else:
        examsList = str(resp.content)[2:-1].split("[")[1][1:-3].split(",")
        count = 0
        first = True
        for x in examsList:
            count += 1
            if count == 5 or first:
                print("\n***EXAM***")
                x = x[1:]
                count = 0
                first = False
            if count == 4:
                x = x[:-1]
            print(x)


def sendtosv():
    path = 'http://localhost:8080/myRESTwsWeb/rest/insertexam'

    print("\nInsert exam values")
    descexam = input("Exam description: ")
    dataexam = input("Exam date: ")
    timeexam = input("Exam time: ")
    locexam = input("Exam Location: ")

    r = requests.post(url=path, json=[descexam, dataexam, timeexam, locexam])
    print(str(r.content)[2:-1])


def modifyExam():
    examindex = input("Insert exam id to modify the description: ")
    description = input("Insert new description: ")
    path = 'http://localhost:8080/myRESTwsWeb/rest/modifyexam'
    r = requests.post(url=path, json=[examindex, description])

    print(str(r.content)[2:-1])


def deleteExam():
    examindex = input("Insert exam id to be deleted: ")
    path = 'http://localhost:8080/myRESTwsWeb/rest/deleteexam'
    r = requests.post(url=path, json=[examindex])

    print(str(r.content)[2:-1])


def readoneexam():
    mustcontinue = True
    values = []

    while mustcontinue:
        option = input("Do u want to search it by id or description partial or full? [id/descp/descf/exit] ")

        if option == "id":
            id = input("Insert id to find: ")
            values.append(option)
            values.append(id)
            printExam(values)
            break

        elif option == "descp":
            desc = input("Insert partial desc to find: ")
            values.append(option)
            values.append(desc)
            printExam(values)
            break

        elif option == "descf":
            desc = input("Insert full desc to find: ")
            values.append(option)
            values.append(desc)
            printExam(values)
            break

        elif option == "exit":
            mustcontinue = False

        else:
            print("\nWrong insert, use one of the available options\n")


def printExam(values):
    path = 'http://localhost:8080/myRESTwsWeb/rest/readoneExam'
    r = requests.post(url=path, json=values)
    request_values = str(r.content)[2:-1].split("[")[1][1:-3].split(",")
    count = 0
    first = True

    print("\nEXAM VALUES:")
    if len(request_values) == 1:
        print("\nexam not found")

    else:
        for x in request_values:
            count += 1
            if count == 5 or first:
                print("\n***EXAM***")
                x = x[1:]
                count = 0
                first = False
            if count == 4:
                x = x[:-1]
            print(x)


def insertgrades():
    values = []
    values.append(input("Insert exam id to relate the grade: "))
    gradestoinsert = input("Number of grades to be inserted ")

    for _ in range(int(gradestoinsert)):
        values.append(input("Insert student id: "))
        values.append(input("Student grade "))

    path = 'http://localhost:8080/myRESTwsWeb/rest/insertgrades'
    r = requests.post(url=path, json=values)

    print(str(r.content)[2:-1])


def downloadallexams():
    resp = requests.get('http://localhost:8080/myRESTwsWeb/rest/readExams')

    if resp.status_code != 200:
        raise print('GET /tasks/ {}'.format(resp.status_code))
    else:
        root = Tk()
        root.withdraw()
        file_name = filedialog.asksaveasfilename()

        with open(file_name, mode='w') as exam_csv:

            examsList = str(resp.content)[2:-1].split("[")[1][1:-3].split(",")
            count = 0
            line = ""
            for x in examsList:
                count += 1
                line += x + " "
                if (count == 5):
                    exam_csv.write(line + "\n")
                    line = ""
                    count = 0


def downloadoneexam():
    mustcontinue = True
    values = []

    while mustcontinue:
        option = input("Do u want to search it by id or description partial or full? [id/descp/descf/exit] ")

        if option == "id":
            id = input("Insert id to find: ")
            values.append(option)
            values.append(id)
            saveExam(values)
            break

        elif option == "descp":
            desc = input("Insert partial desc to find: ")
            values.append(option)
            values.append(desc)
            saveExam(values)
            break

        elif option == "descf":
            desc = input("Insert full desc to find: ")
            values.append(option)
            values.append(desc)
            saveExam(values)
            break

        elif option == "exit":
            mustcontinue = False

        else:
            print("\nWrong insert, use one of the available options\n")


def saveExam(values):
    path = 'http://localhost:8080/myRESTwsWeb/rest/readoneExam'
    r = requests.post(url=path, json=values)
    request_values = str(r.content)[2:-1].split("[")[1][1:-3].split(",")

    print("\nEXAM EXPORTED:")

    if len(request_values) == 1:
        print("\nexam not found")
    else:
        root = Tk()
        root.withdraw()
        file_name = filedialog.asksaveasfilename()

        line = ""
        count = 0
        first = True
        with open(file_name, mode='w') as exam_csv:
            for x in request_values:
                count += 1
                line += x + " "
                if (count == 5):
                    exam_csv.write(line + "\n")
                    line = ""
                    count = 0


def printonestudentgrades():
    path = 'http://localhost:8080/myRESTwsWeb/rest/gradesone'
    studentid = input("Insert the student id to retrieve grades all grades: ")
    r = requests.post(url=path, json=[studentid])
    requestvalues = str(r.content)[2:-2].split("[")[1][:-2].split(",")

    if len(requestvalues) == 1:
        print("\nexam not found")

    else:
        root = Tk()
        root.withdraw()
        file_name = filedialog.asksaveasfilename()

        count = 0
        line = ""
        with open(file_name, mode='w') as exam_csv:
            for x in requestvalues:
                count += 1
                line += x + " "
                if count == 3:
                    count = 0
                    exam_csv.write(line + "\n")
                    line = ""

        exam_csv.close()


def printallgrades():
    path = 'http://localhost:8080/myRESTwsWeb/rest/gradesexam'
    studentid = input("Insert the exam id to retrieve the grades: ")
    r = requests.post(url=path, json=[studentid])
    requestvalues = str(r.content)[2:-2].split("[")[1][:-2].split(",")

    if len(requestvalues) == 1:
        print("\nexam not found")

    else:
        root = Tk()
        root.withdraw()
        file_name = filedialog.asksaveasfilename()

        count = 0
        line = ""
        with open(file_name, mode='w') as exam_csv:
            for x in requestvalues:
                count += 1
                line += x + " "
                if count == 3:
                    count = 0
                    exam_csv.write(line + "\n")
                    line = ""

        exam_csv.close()


def profesor():
    while True:

        print("\n****OPTIONS****")
        print("*** (a) - Read ALL Exams ***")
        print("*** (b) - Insert Exam ***")
        print("*** (c) - Modify Exam ***")
        print("*** (d) - Delete Exam ***")
        print("*** (e) - Read ONE Exam ***")
        print("*** (f) - Insert grade/s ***")
        print("*** (g) - Download all exams ***")
        print("*** (h) - Download one exam ***")
        print("*** (i) - Download all grades from an exam ***")
        print("*** (j) - Download all grades from a student ***")

        print("*** (q) - CLOSE PROGRAM ***")
        option = input("Please select one of the above options: ")

        if option == "a":
            readfromdb()
        elif option == "b":
            sendtosv()
        elif option == "c":
            modifyExam()
        elif option == "d":
            deleteExam()
        elif option == "e":
            readoneexam()
        elif option == "f":
            insertgrades()
        elif option == "g":
            downloadallexams()
        elif option == "h":
            downloadoneexam()
        elif option == "i":
            printallgrades()
        elif option == "j":
            printonestudentgrades()

        elif option == "q":
            print("Quit time")
            break
        else:
            print("\nOption introduced not available, use another one intead")


def student():
    while True:

        print("\n****OPTIONS****")
        print("*** (a) - Read ALL Exams ***")
        print("*** (b) - Read ONE Exam ***")
        print("*** (c) - Download all exams ***")
        print("*** (d) - Download one exam ***")
        print("*** (e) - Download all grades from a student ***")

        print("*** (q) - CLOSE PROGRAM ***")
        option = input("Please select one of the above options: ")

        if option == "a":
            readfromdb()
        elif option == "b":
            readoneexam()
        elif option == "c":
            downloadallexams()
        elif option == "d":
            downloadoneexam()
        elif option == "e":
            printonestudentgrades()
        elif option == "q":
            print("Quit time")
            break
        else:
            print("\nOption introduced not available, use another one intead")


if __name__ == '__main__':

    try:
        path = 'http://localhost:8080/myRESTwsWeb/rest/checkuser'
        studentid = input("Insert ID: ")
        r = requests.post(url=path, json=[studentid])
        usertype = str(r.content)[2:-1]

        if usertype == "true" or usertype == "false":
            if usertype == "true":
                profesor()
            else:
                student()
        else:
            print(usertype)
    except:
        print("Something when wrong while connecting to the server")


