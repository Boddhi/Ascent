#include <iostream>
using namespace std;

int main(){
  int i,j;
  for(i = 0; i < 10; i++){
    for(j = 0; j < 10; j++){
      cout << i << " * " << j << " = " << i*j << "\n";
    }
  }
  return 0;
}
