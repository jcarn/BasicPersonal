import sys
import random
import time

def how_long(max_time):
    my_time = random.randint(1, max_time)
    sis_time = random.randint(1, max_time)
    cur_time = max(my_time, sis_time)
    while my_time != sis_time:
        if cur_time > my_time:
            my_time += random.randint(1, max_time)
        else:
            sis_time += random.randint(1, max_time)
        cur_time = max(my_time, sis_time)
    return my_time

def run(num_sim, max_time):
    total_time = 0
    for i in range(num_sim):
        total_time += how_long(max_time)
    return float(total_time) / num_sim

num_sim = input("Number of Simulations: ")
max_time = input("Maximum Time: ")
run_time = time.clock();
average_time = run(num_sim, max_time)
print "Average time: " + str(round(100 * average_time) / 100)
print time.clock() - run_time;