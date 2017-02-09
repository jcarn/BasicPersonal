def median(in_list):
    sorted_list = sorted(in_list)
    median = 0
    length = len(sorted_list)
    if length % 2 == 0:
        median = (sorted_list[length / 2] + sorted_list[(length / 2) - 1]) / 2
    else:
        median = sorted_list[int(round(length / 2)) - 1]
    return median

print median([1,2,3,4,5])