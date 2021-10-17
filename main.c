#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include "compteEstBon.h"
#define MAX 300

char ops[4] = {'+', '-', '*', '/'};
int operande[6]={1,7,1,2,25,100};
int attendu=220;
int plusproche = 0;
int nombreAppel = 0;
char *buffer;
int main()
{
    buffer = (char*)malloc(MAX*sizeof(char));
    strcat(buffer,"  \nCalcul:\n");
    compteEstBon(operande,6, attendu);
}

bool compteEstBon(int tab[],int nb,int attendu)
{
    nombreAppel++;
    for (int i = 0; i < nb; i++)
    {
        if(tab[i] == attendu){
            return true;
        }
        for(int j = 0; j < nb; j++)
            for (int k = 0; i < 4; k++)
            {
                int res=calculer(k,tab[i],tab[j]);
                if(res>0){
                    if(abs(attendu-plusproche)>abs(attendu-res)){
                        plusproche=res;
                    }
                    int savei=tab[i],savej=tab[j];
                    tab[i]=res;
                    tab[j]=tab[nb-1];

                    int *newtab = (int*)malloc(nb-1 * sizeof(int));
                    for(size_t l = 0; l < nb-1; l++){
                        newtab[l]=tab[l+2];
                    }
                    newtab[nb-1]=res;
                    if(compteEstBon(newtab,nb,attendu)){
                        printf("%d %c %d = %d \n",savei,ops[k],savej,res);
                        return true;
                    }
                    tab[i]=savei;
                    tab[j]=savej;
                }
            }
            
    }
    return false;
}

int calculer(int op, int nb1, int nb2)
{
    switch (op)
    {
    case 0:
        return nb1 + nb2;
        break;
    case 1:
        if (nb1 > nb2)
        {
            return nb1 - nb2;
        }
        else
        {
            return -1;
        }
    case 2:
        return nb1 * nb2;
    case 3:
        if(nb1%nb2 == 0){
            return nb1/nb2;
        }
    }
    return -1;
}