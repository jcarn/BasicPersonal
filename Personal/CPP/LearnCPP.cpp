#include <iostream>

int doubleNumber(int inVal);

int getInput()
{   
    std::cout << "Please enter a number: ";
    int outVal = 0;
    std::cin >> outVal;
    return outVal;
}

int main()
{
    using std::cout;
    int dubVal = getInput();
    cout << doubleNumber(dubVal);   
}