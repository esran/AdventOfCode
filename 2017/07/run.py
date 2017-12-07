#!/usr/bin/env python

lines = []
with open('input.txt', 'r') as file:
    lines = [l.strip() for l in file]

carry = {}
carried_by = {}
programs = []
weights = {}

# Parse the lines into dictionaries and a list of programs
for line in lines:
    words = line.split(" ")
    program = words[0]
    programs.append(program)
    weights[program] = int(words[1][1:-1])
    carry[program] = []
    if len(words) > 2:
        for i in range(3, len(words)):
            if words[i][-1] == ',':
                other = words[i][:-1]
            else:
                other = words[i]
            carry[program].append(other)
            carried_by[other] = program


# Get the root program
root = None
for program in programs:
    if program not in carried_by:
        root = program
        break

print "root: %s" % (root)


def get_weight(program):
    weight = weights[program]
    for sub in carry[program]:
        weight += get_weight(sub)
    return weight


def is_balanced(program):
    if program not in carry:
        return True
    weight = None
    for sub in carry[program]:
        sub_weight = get_weight(sub)
        if weight is None:
            weight = sub_weight
        elif weight != sub_weight:
            return False
    return True


program = root
prev = None
while not is_balanced(program):
    # check for an unbalanced child
    next = None
    for sub in carry[program]:
        if not is_balanced(sub):
            next = sub
            break

    # we found an unbalanced child so continue with that one
    if next is not None:
        prev = program
        program = next
        continue

    # all our children are balanced but I'm not so one of my
    # children is off. Find which one

    # generate the list of children
    children = []
    for sub in carry[program]:
        children.append(sub)

    # go through each child and check how many other children
    # it matches with (including itself)
    for child in range(len(children)):
        matches = 0
        for n in range(len(children)):
            if get_weight(children[child]) == get_weight(children[n]):
                matches += 1
        # if there is only one match then this is the incorrect one
        if matches == 1:
            break

    # get the carry weight of this child and the one of the others
    w1 = get_weight(children[child])
    w2 = get_weight(children[(child + 1) % len(children)])

    # adjust the weight of this program accordingly
    wc = weights[children[child]]
    wc += w2 - w1
    print "%s: %d" % (children[child], wc)
    break
