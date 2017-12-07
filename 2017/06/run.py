#!/usr/bin/env python

with open('input.txt', 'r') as file:
    lines = [l.strip() for l in file]
    banks = map(int, lines[0].split("\t"))

# save a copy of the data
prev = []


# Check whether a set of banks has been seen before
def check_priors(check):
    for i in range(0, len(prev)):
        if check == prev[i]:
            return i

    return -1


# Rebalance a set of banks
def balance(banks):
    # initialise to first bank
    m = 0
    mv = banks[m]

    # find any higher bank
    for i in range(0, len(banks)):
        if banks[i] > mv:
            m = i
            mv = banks[m]

    # zero the chosen bank
    banks[m] = 0

    # distribute the remaining one bank at a time
    for i in range(0, mv):
        m += 1
        m %= len(banks)
        banks[m] += 1

    return banks


# Keep rebalancing until we see a set for a second time
iter = 0
prior = -1
while True:
    # Save this set
    prev.append(banks[:])

    # Rebalance
    banks = balance(banks)

    # Count iterations
    iter += 1

    prior = check_priors(banks)
    if prior >= 0:
        break


print "prior=%d iter=%d diff=%d" % (prior, iter, iter - prior)
