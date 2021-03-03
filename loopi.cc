#include <iostream>
#include <vector>

using namespace std;

int main() {
    auto v = vector<int>();
    v.resize(5);
    for (int i: v) {
        v.pop_back();
        cout << i << endl;
    }
}
