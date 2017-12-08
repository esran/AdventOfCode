#!/usr/bin/env python

lines = []
with open('input.txt', 'r') as file:
    lines = [l.strip() for l in file]

# parse the data
registers = {}
commands = []
for line in lines:
    words = line.split(" ")
    register = words[0]
    op = words[1]
    value = words[2]
    other_reg = words[4]
    comparison = words[5]
    amount = words[6]

    # Add command line
    commands.append({'register': register, 'op': op, 'value': int(value), 'other_reg': other_reg, 'comparison': comparison, 'amount': int(amount)})

    # all registers initialise to zero
    registers[register] = 0

max = 0
for command in commands:
    # debug commands
    # print "%s(%d) %s %d if %s(%d) %s %d" % (
    #     command['register'],
    #     registers[command['register']],
    #     command['op'],
    #     command['value'],
    #     command['other_reg'],
    #     registers[command['other_reg']],
    #     command['comparison'],
    #     command['amount']
    # )

    # Get adjusted value based on command
    if command['op'] == "inc":
        value = command['value']
    else:
        value = - command['value']

    # get the other registers value
    other_val = registers[command['other_reg']]

    if eval("%d %s %d" % (other_val, command['comparison'], command['amount'])):
        registers[command['register']] += value
        # print "%s(%d)" % (command['register'], registers[command['register']])
        if registers[command['register']] > max:
            max = registers[command['register']]

largest = None
for reg in registers:
    value = registers[reg]
    if largest is None:
        largest = value
    else:
        if value > largest:
            largest = value

print largest
print max
