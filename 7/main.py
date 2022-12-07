import re

from anytree import Node


class Dir(Node):
    file_size: int = 0

    def get_size_recursive(self) -> int:
        total = self.file_size
        for child in self.children:
            total += child.get_size_recursive()
        return total

    def get_size_recursive_max_x(self, max_size: int):
        large_things = []
        for child in self.children:
            if (size := child.get_size_recursive()) <= max_size:
                large_things.append(size)
            large_things.extend(child.get_size_recursive_max_x(max_size))
        return large_things

    def get_size_recursive_min_x(self, min_size: int):
        small_things = []
        for child in self.children:
            if (size := child.get_size_recursive()) >= min_size:
                small_things.append(size)
                small_things.extend(child.get_size_recursive_min_x(min_size))
        return small_things


CD_RE = re.compile(r'^\$ cd (.+)$')
DIR_RE = re.compile(r'^dir (.+)$')
FILE_RE = re.compile(r'^(\d+) (.+)$')


def main():
    with open('input.txt') as f:
        lines = f.read().split('\n')

    root = Dir('/')
    cwd = root

    for line in lines:
        if line == '$ cd ..':
            cwd = cwd.parent
        elif match := CD_RE.search(line):
            cwd = Dir(match.group(1), parent=cwd)
        elif match := FILE_RE.search(line):
            cwd.file_size += int(match.group(1))

    available_space = 70000000 - root.get_size_recursive()
    required_space = 30000000 - available_space

    print('Part 1:', sum(root.get_size_recursive_max_x(100000)))
    print('Part 2:', sorted(root.get_size_recursive_min_x(required_space))[0])


if __name__ == '__main__':
    main()
