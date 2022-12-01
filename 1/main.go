package main

import (
	"fmt"
	"os"
	"sort"
	"strconv"
	"strings"
)

func check(e error) {
	if e != nil {
		panic(e)
	}
}

func main() {
	dat, err := os.ReadFile("input.txt")
	check(err)
	input := string(dat)

	inventories := strings.Split(input, "\r\n\r\n") // Developing on Windows is horrible

	var calorie_counts []int

	for _, inventory := range inventories {
		total_calories := 0

		snacks := strings.Split(strings.TrimSpace(inventory), "\r\n")

		for _, snack_str := range snacks {

			snack_int, err := strconv.Atoi(strings.TrimSpace(snack_str))
			check(err)

			total_calories += snack_int
		}

		calorie_counts = append(calorie_counts, total_calories)

	}

	sort.Sort(sort.Reverse(sort.IntSlice(calorie_counts)))

	fmt.Println(calorie_counts[0])

	total := 0

	// I feel like this should be a built-in function? Called Sum or something.
	for _, i := range calorie_counts[:3] {
		total += i
	}

	fmt.Println(total)

}
