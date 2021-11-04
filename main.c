#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include "compteEstBon.h"
#include <time.h>
#define MAX 500

char ops[4] = {'+', '-', '*', '/'};
int tab[6]={1,7,1,2,25,100};
int attendu=591;
int plusproche = 0;
int nombreAppel = 0;
char *buffer;
char snum[10];
clock_t begin,end;
int main(int argc, char*argv[])
{
    if(argc<7){
        printf("Il manque des arguments\n");
        printf("Usage : ./compteEstBon.exe 1 2 3 4 5 6 591\n");
        return 1;
    }
    attendu = atoi(argv[7]);
    
    if(attendu<101|| attendu>999){
        printf("Le nombre attendu doit etre comprise entre 101 et 999\n");
        return 1;
    }
    buffer = (char*)malloc(MAX*sizeof(char));
    strcat(buffer,"  \nCalcul:\n");
    begin = clock();
    if(compteEstBon(6, attendu))
    {
        printf("Le compteEstBon\n");
        printf("%s",buffer);
        end = clock();
    }else{
        printf("Le compte n est pas bon\n");
        printf("le plus proche est : %d \n",plusproche);
        compteEstBon(6,plusproche);
        end = clock();
    }
    
    double time_spent = (double)(end - begin) / CLOCKS_PER_SEC;
    printf("%f temps ecoule",time_spent);
    return 0;
}
bool compteEstBon(int nb,int attendu)
{
    nombreAppel++;
    for (int i = 0; i < nb; i++)
    {
        if(tab[i] == attendu){
            return true;
        }
        for(int j = i+1; j < nb; j++){
            for (int k = 0; k < 4; k++)
            {
                // printf("%d ",tab[i]);
                // printf("%c ",ops[k]);
                // printf("%d =",tab[j]);
                int res=calculer(k,tab[i],tab[j]);
                // printf("%d\n",res);
                if(res>0){
                    if(abs(attendu-plusproche)>abs(attendu-res)){
                        plusproche=res;
                    }
                    int savei=tab[i],savej=tab[j];
                    tab[i]=res;
                    tab[j]=tab[nb-1];

                    if(compteEstBon(nb-1,attendu)){
                        // printf("%d %c %d = %d \n",savei,ops[k],savej,res);
                        itoa(savei,snum,10);
                        strcat(buffer,snum);
                        itoa(ops[k],snum,10);
                        strcat(buffer,snum);
                        itoa(savej,snum,10);
                        strcat(buffer,snum);
                        strcat(buffer," = ");
                        itoa(res,snum,10);
                        strcat(buffer,snum);
                        strcat(buffer,"\n");
                        return true;
                    }
                    tab[i]=savei;
                    tab[j]=savej;
                }
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