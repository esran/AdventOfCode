#!/usr/bin/env python

with open('input.txt', 'r') as file:
    code = [int(l.strip()) for l in file]

# save a copy of the data
code2 = code[:]

# process the first step
p = 0
n = 0
l = len(code)

while p < l and p >= 0:
    n += 1
    d = code[p]
    code[p] += 1
    p += d

print "1. l=%d, n=%d, p=%d" % (l, n, p)

# process the second step
code = code2[:]
p = 0
n = 0
l = len(code)

while p < l and p >= 0:
    n += 1
    d = code[p]
    if d < 3:
        code[p] += 1
    else:
        code[p] -= 1
    p += d

print "2. l=%d, n=%d, p=%d" % (l, n, p)
