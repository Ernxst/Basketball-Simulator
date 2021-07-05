import csv
import statistics

data = []

with open("nba2021_advanced.csv", 'r') as file:
    csv_file = csv.DictReader(file)
    for row in csv_file:
        data.append(dict(row))

sums = {pos: [] for pos in ["PG", "SG", "SF", "PF", "C"]}

for row in data:
    position = row["Pos"].split("-")[0]
    sums[position].append(int(row["Age"]))

for position, data in sums.items():
    average = sum(data) / len(data)
    print(position)
    print("min:", min(data))
    print("max:", max(data))
    print("average:", average)
    print("stdev:", statistics.stdev(data))
    print()
