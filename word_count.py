import sys

if len(sys.argv) != 2:
    print 'usage: python word_count.py input_file'
    sys.exit(1)

wordlist = {}
wordcount = 0
try:
    f = open(str(sys.argv[1]), 'r')
except IOError:
    print 'Unable to find file with the name ' + sys.argv[1]
    sys.exit(2)

for word in f.read().split():
    if word.lower() in wordlist: 
        wordlist[word.lower()] += 1
    else: #new word
        wordlist[word.lower()] = 1
    wordcount += 1
f.close()

for key,value in sorted(wordlist.items()):
    print key,value
print 'there are ' + str(wordcount) + ' words in the file'
