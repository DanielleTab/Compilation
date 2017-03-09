import random
import sys

TESTS_DIR = r"WINDOWS_COMMAND_LINE\FOLDER_10_tester\final_tests"

QUICKSORT_TEMPLATE_FILE_PATH = TESTS_DIR + r"\IC\Quicksort_template_DoNotRun"
QUICKSORT_IC_FILE_PATH = TESTS_DIR + r"\IC\Quicksort"
QUICKSORT_EO_FILE_PATH = TESTS_DIR + r"\ExpectedOutput\Quicksort_EO.txt"

ENCRYPTION_TEMPLATE_FILE_PATH = TESTS_DIR + r"\IC\Encryption_template_DoNotRun"
ENCRYPTION_IC_FILE_PATH = TESTS_DIR + r"\IC\Encryption"
ENCRYPTION_EO_FILE_PATH = TESTS_DIR + r"\ExpectedOutput\Encryption_EO.txt"

LINE_TO_REPLACE = "        /* ---------------------------------- REPLACE THIS LINE WITH INIT ---------------------------------- */\n"
ARRAY_SIZE = 20
ARRAY_RAND_HIGHER_BOUND = 255
ARRAY_STRING_SEPARATOR = 555
RANGE_LENGTH = ARRAY_RAND_HIGHER_BOUND + 1

def writeArrToFile(outputFile, arr):
    for i in range(ARRAY_SIZE):
        outputFile.write("%d " % (arr[i], ))
    outputFile.write("%d " % (ARRAY_STRING_SEPARATOR,))

def writeRandomArrayInitToFile(outputFile, arr):
    for i in range(ARRAY_SIZE):
        arr.append(random.randint(0, ARRAY_RAND_HIGHER_BOUND))
        outputFile.write("\t\ta[%d] = %d;\n" % (i, arr[i], ))
    
def generateICForEncryption(arr):
    randomShifts = [0, 0, 0]
    encryptionTemplateLines = open(ENCRYPTION_TEMPLATE_FILE_PATH, 'r').readlines()
    encryptionICFile = open(ENCRYPTION_IC_FILE_PATH, 'w')
    for line in encryptionTemplateLines:
        if line != LINE_TO_REPLACE:
            encryptionICFile.write(line)
        else:
            writeRandomArrayInitToFile(encryptionICFile, arr)
            encryptionICFile.write("\n\t\tint[] randomShifts = new int[3];\n")
            for i in range(len(randomShifts)):
                randomShifts[i] = random.randint(0, ARRAY_RAND_HIGHER_BOUND)
                encryptionICFile.write("\t\trandomShifts[%d] = %d;\n" % (i, randomShifts[i],))
            encryptionICFile.write("\n\t\treturn randomShifts;\n")
    encryptionICFile.close()
    return randomShifts

def generateEOForEncryption(arr, randomShifts):
    encryptionEOFile = open(ENCRYPTION_EO_FILE_PATH, 'w')
        
    # Original array
    writeArrToFile(encryptionEOFile, arr)

    # Dummy encryptor
    for i in range(2):
        writeArrToFile(encryptionEOFile, arr)

    # random shift encryptor
    shiftedArr = [((i + randomShifts[0]) % RANGE_LENGTH) for i in arr]
    writeArrToFile(encryptionEOFile, shiftedArr)
    writeArrToFile(encryptionEOFile, arr)

    # Complex encryptor
    complexEncryptedArr = []
    encryptors = [0, 0, 0, 0]
    encryptors[0] = lambda x: x
    encryptors[1] = lambda x: x
    encryptors[2] = lambda x: ((x + randomShifts[1]) % RANGE_LENGTH)
    encryptors[3] = lambda x: ((x + randomShifts[2]) % RANGE_LENGTH)
    for i in range(len(arr)):
        complexEncryptedArr.append(encryptors[i % 4](arr[i]))
    writeArrToFile(encryptionEOFile, complexEncryptedArr)
    writeArrToFile(encryptionEOFile, arr)
    
    encryptionEOFile.close()
    
def generateICAndEOForEncryption():
    arr = []
    randomShifts = generateICForEncryption(arr)
    generateEOForEncryption(arr, randomShifts)
    
def generateICForQuicksort(arr):
    quicksortTemplateLines = open(QUICKSORT_TEMPLATE_FILE_PATH, 'r').readlines()
    quicksortICFile = open(QUICKSORT_IC_FILE_PATH, 'w')
    for line in quicksortTemplateLines:
        if line != LINE_TO_REPLACE:
            quicksortICFile.write(line)
        else:
            writeRandomArrayInitToFile(quicksortICFile, arr)
    quicksortICFile.close()
    
def generateEOForQuicksort(arr):
    # Writing the original array and the sorted array
    quicksortEOFile = open(QUICKSORT_EO_FILE_PATH, 'w')
    writeArrToFile(quicksortEOFile, arr)
    arr.sort()
    writeArrToFile(quicksortEOFile, arr)
    quicksortEOFile.close()
    
def generateICAndEOForQuicksort():
    arr = []
    generateICForQuicksort(arr)
    generateEOForQuicksort(arr)
    
if __name__ == "__main__":
        testName = sys.argv[1]
        if testName == "Encryption":
            print("Generating IC and EO for the test 'Encryption'")
            generateICAndEOForEncryption()
        elif testName == "Quicksort":
            print("Generating IC and EO for the test 'Quicksort'")
            generateICAndEOForQuicksort()
        else:
            print("Thes test " + testName + " is not supported")

