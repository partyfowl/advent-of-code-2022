#!/usr/bin/env bash

set -eu

priority_sum=0
badge_priority_sum=0
group_mem=0

get_priority () {
    ascii_value=$(printf %d \'"$1")
    
    if (( ascii_value > 90 ))
    then
        priority=$((ascii_value-96))
    else
        priority=$((ascii_value-38))
    fi
    echo $priority
}

# for var in $(cat input.txt | head -12)
while IFS= read -r var
do
    group_mem=$((group_mem+1))

    if ((group_mem == 1))
    then
        group="$var"
    else
        group="$group $var"
    fi

    if ((group_mem == 3))
    then
        # shellcheck disable=SC2086
        set -- $group
        badge=$(echo "$1" | grep -o "[$2]" | tr -d '\n' | grep -o "[$3]")
        badge_priority=$(get_priority "$badge")
        badge_priority_sum=$((badge_priority_sum+badge_priority))
        group_mem=0
    fi

    character=$(echo "${var:0:${#var}/2}" | grep -o "[${var:${#var}/2}]" | head -1)
    priority=$(get_priority "$character")
    priority_sum=$((priority_sum+priority))
    
done < input.txt

echo "Part 1: $priority_sum"
echo "Part 2: $badge_priority_sum"
