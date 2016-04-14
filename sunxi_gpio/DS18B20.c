#include <linux/delay.h>
#include <mach/gpio.h> /* for new api */
#include <linux/gpio.h> /* for new api */
#include <asm/io.h>
#include <linux/kthread.h>


#include "DS18B20.h"
#include "private.h"

#define DS18B20_DELAY	 /*1000*//*500*//*250*//*125*/6

extern unsigned char DS18B20_DQ;
extern unsigned char DS18B20_TEMPERATURE_DATA;




static int getGpio(unsigned char gpio)
{
	return __gpio_get_value(gpio);
}
static int setDQ(unsigned char volue)
{
	if(DS18B20_DQ!=0)
	{
		gpio_direction_output(DS18B20_DQ,volue);	
		return 0;
	}
	else
		return -1;
}
static int getDQ(void)
{
	gpio_direction_input(DS18B20_DQ);
	return getGpio(DS18B20_DQ);
}

void DS18B20init(void)
{
	char x=1;
#if 1	
	setDQ(1);
	udelay(DS18B20_DELAY*8);		/*������ʱ*/
	setDQ(0);
	udelay(DS18B20_DELAY*80);		/*��ʱ>480us 540us*/
	setDQ(1); 						/*�������� 15-60us*/
	udelay(DS18B20_DELAY*10);
	x=getDQ();						/*������״̬ Ϊ0��λ�ɹ���Ϊ1�򲻳ɹ� */
	//MULTGAS_DEBUG(DS18B20_DEBUG_BH "x = %d ",x);
	udelay(DS18B20_DELAY*35);
	setDQ(1);						/*�ͷ�����*/
#endif	
}
static unsigned char DS18B20readOneChar(void)
{

	unsigned char i=0;
	unsigned char dat = 0;
//	udelay(1);
	for (i=8;i>0;i--)
	{
		setDQ(0); // �������ź�
		dat>>=1;
//		udelay(1);
		setDQ(1); // �������ź�
		if(getDQ())
			dat|=0x80;
		udelay(DS18B20_DELAY*5/*5*/);
	}
	 return(dat);
}
static void DS18B20writeOneChar(unsigned char dat)
{
#if 1
	unsigned char i=0;
//	udelay(1);
	for (i=8; i>0; i--)
	{
		setDQ(0);
		setDQ(dat&0x01);
		udelay(DS18B20_DELAY*5/*5*/);
		setDQ(1);
		dat>>=1;
	}
	udelay(DS18B20_DELAY*5/*5*/);
#endif	
}
/* ��ȡ�¶�ֵ*/
/* ÿ�ζ�д��Ҫ�ȸ�λ*/
int DS18B20readOneTemperature(void) 
{
#if 1
	unsigned char a=0;
	unsigned char b=0;
	unsigned char t=0;
	
	float tt=0;
	DS18B20init();
	DS18B20writeOneChar(0xCC); // ����������кŵĲ���
	DS18B20writeOneChar(0x44); // �����¶�ת��
	udelay(DS18B20_DELAY*200);
	DS18B20init();
	DS18B20writeOneChar(0xCC); //����������кŵĲ��� 
	DS18B20writeOneChar(0xBE); //��ȡ�¶ȼĴ����ȣ����ɶ�9���Ĵ����� ǰ���������¶�
	a=DS18B20readOneChar();
	b=DS18B20readOneChar();
	//MULTGAS_DEBUG(DS18B20_DEBUG_BH " 0x%02X ",a);
	//MULTGAS_DEBUG(DS18B20_DEBUG_BH " 0x%02X ",b);
	b<<=4;
	b+=(a&0xf0)>>4;
	t=b;
	tt=t*0.0625;
	t =tt*10+0.5; //�Ŵ�10���������������
	//MULTGAS_DEBUG(DS18B20_DEBUG_BH " t = %d",t);
	return t;
#endif
}




