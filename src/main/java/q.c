#define I 100
#define J 100
void Q(){
    double a[I][J];
    double n[I];
    double x[I][J];
    double y[J];

    for( int i=0; i<100;i++ ){
        y[i] = a[i][0] * x[i][0] * ( t(a , x) ^ (n[i] -1) );
    }

}
double t( double a[][], double x[][]){
    double sum = 0.0;
    for( int i=0; i<I;i++ ){
        for( int j=0; j<J ; j++ ){
            sum += a[i][j] + x[0][j];
        }
    }
    return sum;
}