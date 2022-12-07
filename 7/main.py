import re

from anytree import Node


CD_RE = re.compile(r'^\$ cd (.+)$')
FILE_RE = re.compile(r'^(\d+) (.+)$')


class Dir(Node):
    file_size: int = 0


    def get_size_recursive(self) -> int:
        total = self.file_size
        for child in self.children:
            total += child.get_size_recursive()
        return total


    def get_size_recursive_min_max_x(self, boundary_size: int, minimum: bool):
        small_things = []
        for child in self.children:
            size = child.get_size_recursive()
            add_to_list = size >= boundary_size if minimum else size <= boundary_size
            if add_to_list:
                small_things.append(size)
            small_things.extend(child.get_size_recursive_min_max_x(boundary_size, minimum))
        return small_things


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

    max_dir_size = 100000

    print('Part 1:', sum(root.get_size_recursive_min_max_x(max_dir_size, False)))

    total_disk_size = 70000000
    update_size = 30000000

    available_space = total_disk_size - root.get_size_recursive()
    required_space = update_size - available_space

    print('Part 2:', sorted(root.get_size_recursive_min_max_x(required_space, True))[0])


if __name__ == '__main__':
    main()
