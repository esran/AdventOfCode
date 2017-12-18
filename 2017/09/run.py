#!/usr/bin/env python

lines = []
with open('input.txt', 'r') as file:
    lines = [l.strip() for l in file]

line = lines[0]
states = []
total = 0
cancelled = 0
depth = 0

for ch in line:
    # Simple case when we're at the start of the string
    if len(states) == 0:
        if ch == '{':
            depth += 1
            total += depth
            states.append(ch)
        elif ch == '<':
            states.append(ch)
        elif ch == '!':
            states.append(ch)
        continue

    else:
        state = states[-1]

        # simple case when we're ignoring the next char
        if state == '!':
            states.pop()
            continue

        # if we're in garbage then we need to check for
        # the ignore or end of garbage marker, otherwise
        # increment the cancelled count
        if state == '<':
            if ch == '!':
                states.append('!')
            elif ch == '>':
                states.pop()
            else:
                cancelled += 1
            continue

        # technically, the only other state we can be in
        # is the block state but check anyway. In block state
        # we need to transition to sub-block, garbage, or
        # back out of the current block
        if state == '{':
            if ch == '<':
                states.append('<')
            if ch == '{':
                depth += 1
                total += depth
                states.append(ch)
            elif ch == '}':
                depth -= 1
                states.pop()

print "total = %d" % (total)
print "cancelled = %d" % (cancelled)
