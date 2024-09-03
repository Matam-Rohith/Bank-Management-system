#include<stdio.h>
#include<stdlib.h>
#include<windows.h>
#include<string.h>
#include<conio.h>
#include"bank1.h"
int main()
{
	int choice;
	int i;
	int num;
	int opt;
	char captcha[20],*captcha1[11]={"hello123","asdf9","qwerty45","hgjf12","system78","world98","free29","customer76","sweet67","root45","reverse85"};
	printf("\n\n\t\t\t* W E L C O M E   T O    B A N K    M A N A G E M E N T    S Y S T E M *  \n");
	printf("\n\t\t\t\t\t\t\t\t\t\t - your perfect banking partner\n");
	printf("\n\n\n");
	printf("\tEnter number from 1 to 10: ");
	scanf("%d",&num);
	if(num>10)
	{
		printf("\n\n\n\t\t\t\t\tInvalid choice,Please enter number (1-10)\n");
		fordelay(1000000000);
		clear_screen();
		main();
	}
	printf("\n");
	printf("\t\t\t\t\t\tcaptcha: %s",captcha1[num]);
	printf("\n\n\tPlease enter the above captcha: ");
	scanf("%s",captcha);
	if(strcmp(captcha,captcha1[num])==0)
	{
		printf("\n");
		printf("\n\n\t\t\t\t\t\tCaptcha matched\t\t\n");
		printf("\n\n");
		fordelay(1000000000);
		printf("\t\t\t\t\tIt is verified that you are human\t\t\t");
		printf("\n\n");
		fordelay(1000000000);
		printf("\n\t\t\t\t\t  Redirecting you to main menu \t\t\t\n");
		printf("\n\n\n");
		fordelay(1000000000);
		printf("\t\t\t\t\t\t KINDLY WAIT ");
		for(i=0;i<5;i++)
		{
			fordelay(1000000000);
			printf(".");
		}
		clear_screen();
	    setcolor(12);
	    printf("\n\n");
	    printf("\n\t\t            ***      W E L C O M E  T O  S R U  B A N K       ***            \t\n");
	    setcolor(15);
	    printf("\n");
		printf("====================================================================================================================\n");
		while(1)
		{
			display_menu();
			scanf("%d",&choice);
			switch(choice)
			{
				case 1:
					add_customer();
					break;
				case 2:
					display_specific_customer();
					break;
				/*case 3:
					delete_customer();
					break;*/
				case 3:
					deposit_money();
					break;
				case 4:
					withdraw_money();
					break;
				case 5:
					display_all_customers();
					break;
				case 6:
					modify_customer();
					break;
				case 7:
					online_transaction();
					break;
				case 8:
					write_fcustomers();
					break;
				case 9:
			    	read_fcustomers();
					break;
				case 10:
					clear_screen();
					setcolor(12);
					printf("\n\n\n\n");
					printf("\t\t\t\t  E X I T I N G   T H E   P R O G R A M  \t\t\t\n");
					printf("\n\n\n");
					fordelay(1000000000);
					printf("\t\t\t\t*** T H A N K Y O U   V I S I T   A G A I N ***\t\t\t\n");
					printf("\n\n\n\n");
					setcolor(15);
					exit(0);
					break;
				default:
					setcolor(12);
					printf("Invalid choice");
					setcolor(15);
					break;
			}
			     
				printf("\n");
		}	
	}
	else
	{
		pass_check();
	}
	
	return 0;
}
