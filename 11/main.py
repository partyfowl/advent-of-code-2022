import yaml


def process_operation(old: int, operation: str):
    a, op, b = operation.split()
    a = old if a == 'old' else int(a)
    b = old if b == 'old' else int(b)

    if op == '+':
        return a + b
    elif op == '*':
        return a * b


def do_part(part: int):
    with open('input.txt') as f:
        monkey_yamls = f.read().replace('  ', '').strip().split('\n\n')

    # Transforms this into something usable
    # I should probably create a class for this
    monkeys = [dict(
        items=[int(__) for __ in str(_['Starting items']).split(', ')],
        if_true=int(_['If true'].split()[-1]),
        if_false=int(_['If false'].split()[-1]),
        test_divisible_by=int(_['Test'].split()[-1]),
        operation=_['Operation'][6:],
        items_inspected=0
    ) for _ in [yaml.safe_load(_.split('\n', 1)[-1]) for _  in monkey_yamls]]

    mod = 1
    for monkey in monkeys:
        mod *= monkey['test_divisible_by']

    for _ in range(20 if part == 1 else 10000):
        for monkey in monkeys:
            while monkey['items']:
                monkey['items_inspected'] += 1
                item = monkey['items'].pop(0)
                item = process_operation(item, monkey['operation'])

                if part == 1:
                    item //= 3
                else:
                    item %= mod
                
                if item % monkey['test_divisible_by'] == 0:
                    next_monkey_index = monkey['if_true']
                else:
                    next_monkey_index = monkey['if_false']

                monkeys[next_monkey_index]['items'].append(item)

    ordered_monkeys = sorted(monkeys, key=lambda _: _['items_inspected']) 

    answer = ordered_monkeys[-2]['items_inspected'] * ordered_monkeys[-1]['items_inspected']

    print(f'Part {part}:', answer)


if __name__ == '__main__':
    for i in range(2):
        do_part(i+1)
