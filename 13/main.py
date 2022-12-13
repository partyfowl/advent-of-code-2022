import json
from typing import Union
from functools import cmp_to_key


def load(packet: str) -> Union[str, list]:
    if packet.startswith('['):
        return json.loads(packet)
    elif packet:
        return int(packet)


def compare(left, right) -> int:
    if type(left) is type(right):
        if type(left) is int:
            return right - left
        else:
            try:
                for i in range(0, len(left)):
                    if comparison := compare(left[i], right[i]):
                        return comparison
                # We get here if we run out of items in the left list
                # So we need to check if there's still items in the right list
                if len(right) > len(left):
                    # If we've got items in the right list, all good
                    return 1
                else:
                    # If we've got the same amount of items in each list,
                    # then more comparisons are needed
                    return 0

            except IndexError:
                # This means we've run out of items in the right list before the left
                return -1

    elif type(left) is int:
        return compare([left], right)
    else:
        return compare(left, [right])


def part1():
    with open('input.txt') as f:
        lines = f.read().strip()
    
    pairs = lines.split('\n\n')

    index = 0
    total = 0

    for pair in pairs:
        index += 1
        left, right = (load(_) for _ in pair.split('\n'))
        if compare(left, right) > 0:
            total += index

    print('Part 1:', total)


def part2():
    with open('input.txt') as f:
        lines = f.read().strip()
    
    packets = [
        load('[[2]]'),
        load('[[6]]')
    ]

    for line in lines.splitlines():
        if line := line.strip():
            packets.append(load(line))
    
    packets = sorted(packets, key=cmp_to_key(compare), reverse=True)
    
    index = 0
    decoder = 1

    for packet in packets:
        index += 1
        if packet == [[2]] or packet == [[6]]:
            decoder *= index
    
    print('Part 2:', decoder)


if __name__ == '__main__':
    part1()
    part2()