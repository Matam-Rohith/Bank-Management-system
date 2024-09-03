struct customer
{
	int account_number;
	char name[50];
	char address[200];
	float balance;
	char phone[11];
	char pin[5];
} ;
struct customer *customers[100];
int num_customers=0;
int i,j;

// Function for new screen
void clear_screen()
{
	system("cls");
}

// Function to set color of text on screen
void setcolor(int color)
{
	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hConsole, color);
}
 // Function to delay
void fordelay(int j)
{
	int k;
	for(i=0;i<j;i++)
	{
		k=i;
	}
}

// Function for display menu chart
void display_menu()
{ 
    setcolor(9);
    printf("\n\n");
    printf("\t\t\t\t\t M A I N  M E N U \t\t\t");
    printf("\n\n");
    setcolor(14);
	printf("\t1.\tAdd customer account\t\n");
	printf("\t2.\tDisplay specific customer account\t\n");
	printf("\t3.\tDeposit money into customer account\t\n");
	printf("\t4.\tWithdraw money from customer account\t\n");
	printf("\t5.\tDisplay all  customer details\t\n");
	printf("\t6.\tModifying customer details\t\n");
	printf("\t7.\tOnline transactions\n");
	printf("\t8.\tStoring customer data into files\t\n");
	printf("\t9.\tLoading customer data in file\t\n");
	printf("\t10.\tExit\t\n");
	printf("\n\n");
	setcolor(10);
	printf("\tEnter your choice: ");
}

// Function to add a new customer
void add_customer()
{
	int i;
	int index=0;
	char pin[5];
	if(num_customers==101)
	{
		printf("\terror: Maximum limit reached\n");
	}
	struct customer new_customer8=(struct customer)malloc*(sizeof(struct customer));
	if(new_customer == NULL)
	{
		printf("Memory allocation failed");
		return;
	}
	clear_screen();
	setcolor(14);
	printf("\n\n");
	printf("\t\t\t\t\tAdding new customer account\t\t\t\n");
	printf("\n");
	setcolor(11);
	printf("\tEnter account number: ");
	scanf("%d",&new_customer->account_number);
	for(i=0;i<num_customers;i++)
	{
		if(customers[i]->account_number==new_customer->account_number)
		{
			printf("\n\n\n\tcustomer with the same account number already exists.\n");
			fordelay(1000000000);
			clear_screen();
			return;
		}
	}
	printf("\n");
	printf("\tEnter name: ");
	scanf("%s",new_customer->name);
	printf("\n");
	printf("\tEnter opening balance: ");
	scanf("%f",&new_customer->balance);
	printf("\n");
	printf("\tEnter address: ");
	scanf("%s",new_customer->address);
	printf("\n");
	do{
		printf("\n");
		setcolor(11);
		printf("\tEnter mobile number (10 digits): ");
		scanf("%s",new_customer->phone);
		if(strlen(new_customer->phone)!=10)
		{
			printf("\n\n");
			setcolor(15);
			printf("\tInvalid mobile number.Please enter 10 digits.\n");
		}
	}while(strlen(new_customer->phone)!=10);
	printf("\n");
	setcolor(11);
	//printf("\tenter pin: ");
	//scanf("%s",new_customer->pin);
	/*do{
		printf("\n");
		setcolor(11);
		printf("\tenter pin (4 digits): ");
		scanf("%s",new_customer->pin);
		if(strlen(new_customer->pin)!=4)
		{
			printf("\n\n");
			setcolor(15);
			printf("Invalid PIN number.Please enter 4 digits.\n");
		}
	}while(strlen(new_customer->pin)!=4); */
	printf("\tEnter pin: ");
	while(index<4)
	{
		pin[index]=getch();
		printf("*");
		index++;
	}
	pin[index]='\0';
	strcpy(new_customer->pin,pin);
	printf("\n");
	
	customers[num_customers++]=new_customer;
	setcolor(10);
	printf("\n");
	fordelay(1000000000);
	printf("\t\tCustomer added successfully\t\t\n");
	fordelay(1000000000);
	setcolor(15);
	printf("\n");
	clear_screen();
}

// Function to find a specific customer and print onto the screen
struct customer* find_customer(int account_number)
{
	for(i=0;i<num_customers;i++)
	{
		if(customers[i]->account_number == account_number)
		{
			return customers[i];
		}
	}
	return NULL;
}

// Function to delete customer account
/*void delete_customer()
{
	int j;
	int account_number;
	clear_screen();
	setcolor(12);
	printf("\n");
	printf("Deleting the customer account\n");
	printf("\n");
	printf("enter customer account number");
	scanf("%d",&account_number);
	printf("\n");
	struct customer *customer=find_customer(account_number);
	
	if(customer==NULL)
	{
		printf("error: no more accounts left");
		return;
	}
	
	int flag=0;
	for(i=0;i<num_customers;i++)
	{
		if(customers[i]->account_number==account_number)
		{
			free(customers[i]);
			customers[i]==NULL;
			flag=1;
			break;
		}
	}
	if(flag=1)
	{
		setcolor(12);
		printf("customer account deleted successfully\n");
		printf("\n");
		setcolor(15);
	}
	else
	{
		printf("customer account not found");
	}
	for(i=0;i<num_customers;i++)
	{
		if(customers[i]!=NULL)
		{
			customers[j++]=customers[i];
		}
	}
	num_customers=j;
	printf("\n");
	
}*/

// Function to deposit money into customers account
void deposit_money()
{
	int account_number;
	float amount;
	clear_screen();
	setcolor(10);
	printf("\n\n");
	printf("\t\t\t\t\tDepositing money into customer account\t\t\t\n");
	printf("\n");
	setcolor(14);
	printf("\tEnter account number: ");
	scanf("%d",&account_number);
	printf("\n");
	
	struct customer *customer=find_customer(account_number);
	if(customer==NULL)
	{
		printf("\n\n");
		printf("\t\tCustomer account not found\t\t\n");
		fordelay(10000000000);
		clear_screen();
		return;
	}
	
	printf("\tEnter amount to deposit: ");
	scanf("%f",&amount);
	
	customer->balance+=amount;
	printf("\n");
	setcolor(11);
	printf("\t\tDeposit Succesfull.New Balance is %.2f\n",customer->balance);
	fordelay(1000000000);
	printf("\n");
	setcolor(15);
	clear_screen();
}

// Function to withdraw money from customer account
void withdraw_money()
{
	int account_number;
	char pin[5];
	float amount;
	int index=0;
	clear_screen();
	setcolor(14);
	printf("\n\n");
	printf("\t\t\t\t\tWithdrawing money from customer account\t\t\t\n");
	printf("\n");
	setcolor(10);
	printf("\tEnter account number: ");
	scanf("%d",&account_number);
	printf("\n");
	//printf("\tenter pin: ");
	//scanf("%s",pin);
	printf("\tEnter pin: ");
	while(index<4)
	{
		pin[index]=getch();
		printf("*");
		index++;
	}
	pin[index]='\0';
	printf("\n");
	
	struct customer *customer=find_customer(account_number);
	if(customer==NULL || strcmp(customer->pin,pin)!=0)
	{
		printf("\n\n\n");
		printf("\t\tCustomer account not found\t\t\n");
		fordelay(1000000000);
		clear_screen();
		return;
	}
	printf("\n");
	printf("\tEnter amount to withdraw: ");
	scanf("%f",&amount);
	printf("\n");
	
	if(customer->balance < amount)
	{
		printf("\n\n\n");
		printf("\t\tInsufficient balance\t\t\n");
		fordelay(1000000000);
		clear_screen();
		return;
	}
	
	customer->balance-=amount;
	printf("\n");
	setcolor(11);
	printf("\t\tWithdrawal Succesfully Completed.New balance is %.2f\n",customer->balance);
	printf("\n");
	fordelay(1000000000);
	setcolor(15);
	clear_screen();
}

// Function to display all customers
void display_all_customers()
{
	clear_screen();
	setcolor(9);
	printf("\n\n");
	if(num_customers==0)
	{
		printf("\n\t\tNo customer accounts\n");
		fordelay(10000000000);
	}
	printf("\n\n");
	printf(" \t      Customer accounts      \t\n");
	setcolor(15);
	printf("======================================================================\n");
	setcolor(10);
	for(i=0;i<num_customers;i++)
	{
		setcolor(10);
		printf("\tAccount number: %d\n",customers[i]->account_number);
		printf("\tCustomer name: %s\n",customers[i]->name);
		printf("\tBalance: %.2f\n",customers[i]->balance);
		printf("\tCustomer address: %s\n",customers[i]->address);
		printf("\tPhone number: %s\n",customers[i]->phone);
		setcolor(15);
		printf("==================================================================\n");
	}
	setcolor(15);
	fordelay(10000000000);
	clear_screen();
}

// Function to save customers data into file
void write_fcustomers()
{
	FILE *fp;
	clear_screen();
	setcolor(14);
	fp=fopen("customers.txt","w");
	if(fp==NULL)
	{
		printf("\n\n\n");
		printf("\t\t\t the file cant be opened\t\t\t\n");
		return;
	}
	for(i=0;i<num_customers;i++)
	{
		fprintf(fp,"account number=%d\nname=%s\naddress=%s\nbalance=%.2f\nphonenumber=%s\n\n",customers[i]->account_number,customers[i]->name,customers[i]->address,customers[i]->balance,customers[i]->phone);
	}
	fclose(fp);
	setcolor(6);
	printf("\n\n\n");
	printf("\t\t\t\t\t Customers data saved into file\t\t\t\t\n");
	printf("\n");
	fordelay(1000000000);
	setcolor(15);
	clear_screen();
}

// Function to load customers data into file
void read_fcustomers()
{
	FILE *fp;
	clear_screen();
	setcolor(1);
	fp=fopen("customers.txt","r");
	if(fp==NULL)
	{
		printf("\n\n\n");
		printf("\t\t\tthe file cant be opened\t\t\t\n");
		return;
	}
	while(!feof(fp) && num_customers<100)
	{
		struct customer new_customer=(struct customer)malloc(sizeof(struct customer));
		if(new_customer==NULL)
		{
			printf("memory allocation failed\n");
			return;
		}
		fscanf(fp,"account number=%d\nname=%s\naddress=%s\nbalance=%f\nphonenumber=%s\n\n",&new_customer->account_number,new_customer->name,new_customer->address,&new_customer->balance,new_customer->phone);
		customers[num_customers++]=new_customer;
	}
	fclose(fp);
	setcolor(15);
	printf("\n\n\n");
	printf("\t\t\t\t\tcustomer data loaded from file\t\t\t\t\n");
	printf("\n");
	fordelay(1000000000);
	setcolor(15);
	clear_screen();
}

// Function to modify the customer details
void modify_customer()
{
	int account_number;
	clear_screen();
	setcolor(13);
	printf("\n\n");
	printf("\t\t\t\t\tModifying customer details\t\t\t\t\n");
	printf("\n");
	setcolor(9);
	printf("\tEnter account number: ");
	scanf("%d",&account_number);
	printf("\n");
	
	int flag1=0;
	for(i=0;i<num_customers;i++)
	{
		if(customers[i]->account_number==account_number)
		{
			printf("\tEnter new name: ");
			scanf("%s",customers[i]->name);
			printf("\n");
			printf("\tEnter new address: ");
			scanf("%s",customers[i]->address);
			printf("\n");
			//printf("\tEnter new phone number: ");
			//scanf("%s",customers[i]->phone);
			do{
				printf("\n");
				setcolor(11);
				printf("\tEnter new mobile number (10 digits): ");
				scanf("%s",customers[i]->phone);
				if(strlen(customers[i]->phone)!=10)
				{
					printf("\n\n");
					setcolor(15);
					printf("\tInvalid mobile number.Please enter 10 digits.\n");
				}
			}while(strlen(customers[i]->phone)!=10);
			printf("\n");
			flag1=1;
			setcolor(10);
			printf("\t\t\tcustomer details updated successfully\t\t\t\n");
			setcolor(15);
			break;
		}
	}
	fordelay(1000000000);
	clear_screen();
	if(flag1==0)
	{
		setcolor(10);
		printf("\n\n\n");
		printf("\t\t\t\tcustomer account not found\t\t\t\t\n");
		printf("\n");
		setcolor(15);
		fordelay(1000000000);
		clear_screen();
	}
}

// Function to display specific customer
void display_specific_customer()
{
	int account_number;
	clear_screen();
	setcolor(14);
	printf("\n\n");
	printf("\t\t\t\t\tDisplaying specific customer details\t\t\t\t\n");
	printf("\n\n");
	setcolor(11);
	printf("\t\tEnter account number: ");
	scanf("%d",&account_number);
	printf("\n");
	
	int flag2=0;
	setcolor(10);
	for(i=0;i<num_customers;i++)
	{
		if(customers[i]->account_number==account_number)
		{
			printf("\n\n");
			setcolor(15);
			printf("\t\t\t\tCustomer Details:\t\t\t\t\n");
			printf("\n\n");
			setcolor(10);
			printf("\t\tAccount number: %d\n",customers[i]->account_number);
			printf("\n");
			printf("\t\tName: %s\n",customers[i]->name);
			printf("\n");
			printf("\t\tAddress: %s\n",customers[i]->address);
			printf("\n");
			printf("\t\tBalance: %.2f\n",customers[i]->balance);
			printf("\n");
			printf("\t\tPhone number: %s\n",customers[i]->phone);
			printf("\n");
			flag2=1;
			break;
		}
	}
	fordelay(10000000000);
	clear_screen();
	if(flag2==0)
	{
		setcolor(12);
		printf("\n\n\n");
		printf("\t\t\t\t customer account not found \t\t\t\n");
		printf("\n");
		fordelay(1000000000);
		setcolor(15);
		clear_screen();
	}
}

// Fuction for online UPI transaction
void online_transaction()
{
	int p;
	char phone[11];
	char phone1[11];
	char pin[5];
	float amount;
	int index=0;
	clear_screen();
	setcolor(13);
	printf("\n\n");
	printf("\t\t\t\t\tOnline UPI Transaction\t\t\t\t\n");
	printf("\n");
	setcolor(15);
	printf("\tEnter mobile number of senders: ");
	scanf("%s",phone);
	printf("\n");
	printf("\tEnter mobile number of receivers: ");
	scanf("%s",phone1);
	printf("\n");
	printf("\tEnter amount to transfer: ");
	scanf("%f",&amount);
	printf("\n\n\n\n");
	setcolor(14);
	printf("\t\t\t\t EVALUATING YOUR CREDENTIALS ");
	for(p=0;p<6;p++)
	{
		fordelay(1000000000);
		printf(".");
	}
	printf("\n\n\n");
	setcolor(3);
	//printf("\tenter pin: ");
	//scanf("%s",pin);
	printf("\tEnter pin: ");
	while(index<4)
	{
		pin[index]=getch();
		printf("*");
		index++;
	}
	pin[index]='\0';
	printf("\n");
	int flag3=0;
	for(i=0;i<num_customers;i++)
	{
		if(strcmp(customers[i]->phone,phone)==0 && customers[i]->balance>amount)
		{
			customers[i]->balance-=amount;
			flag3=1;
			break;
		}
	}
	if(flag3==0)
	{
		setcolor(2);
		printf("\n\n");
		printf("\t\t\toops something went wrong\t\t\t\n");
		fordelay(1000000000);
		setcolor(15);
		clear_screen();
	}
	
	int flag4=0;
	for(i=0;i<num_customers;i++)
	{
		if(strcmp(customers[i]->phone,phone1)==0)
		{
			customers[i]->balance+=amount;
			flag4=1;
			break;
		}
	}
	if(flag4==0)
	{
		setcolor(7);
		printf("\n\n");
		printf("\t\t\toops something went wrong\t\t\t\n");
		fordelay(1000000000);
		setcolor(15);
		clear_screen();
	}
	if(flag3==1 && flag4==1)
	{
		setcolor(9);
		printf("\n\n");
		fordelay(1000000000);
		printf("\t\t\tOnline transaction successfull\t\t\t\n");
		fordelay(1000000000);
		printf("\n");
		setcolor(15);
		clear_screen();
	}
	
}

// Function to check the password
void pass_check()
{
	int opt;
	printf("\n\t\t\t\t Invalid captcha ");
	printf("\n\n\n");
	fordelay(1000000000);
	printf("\tPlease enter \n");
	printf("\n\t1.tryagain");
	printf("\n\t2.exit");
	printf("\n\tChoose you option: ");
	scanf("%d",&opt);
	if(opt==1)
	{
		clear_screen();
		main();
	}
	else
	{
		clear_screen();
		printf("\n\t\t\texiting program");
		exit(0);
	}
	
}
