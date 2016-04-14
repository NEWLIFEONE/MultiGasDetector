/********************************************************************************
 Author        : CAST 

 Date          : 2011-3-21

 Hardware      : ADuC7026+AD7794	
********************************************************************************/

#ifndef AD7794_H
#define AD7794_H

//----------------------
//Command and Register Map
//----------------------
#define COMMUNICATIONS	0x00
#define STATUS			0x08	// 	 binary:1000, the last 3 bit (000) is same as COMMUNICATIONS register
#define MODE			0x01
#define CONFIGURATION	0x02
#define DATA			0x03
#define ID				0x04
#define IO				0x05
#define OFFSET			0x06
#define FULLSCALE		0x07

//COMMUNICATIONS register
#define WRITE_DISABLE 		0x80
#define WRITE_ENABLE 		0x00

#define READ_DATA				0x40
#define WRITE_DATA				0x00

#define RS_COMMUNICATIONS	0x00
#define RS_STATUS			0x00			
#define RS_MODE				0x08
#define RS_CONFIGURATION	0x10
#define RS_DATA				0x18
#define RS_ID				0x20
#define RS_IO				0x28
#define RS_OFFSET			0x30
#define RS_FULLSCALE		0x38

#define CREAD_ENABLE		0x04
#define CREAD_DISABLE		0x00
	
//STATUS register	
#define DATA_RDY	0x80
#define ERR			0x40
#define NOXREF		0x20
#define SR4			0x10	// 0, fix
#define SR3			0x08	// 0 for AD7795, 1 for AD7794, fix
#define CH_AIN1	0x0000
#define CH_AIN2	0x0001
#define CH_AIN3	0x0002
#define CH_AIN4	0x0003
#define CH_AIN5	0x0004
#define CH_AIN6	0x0005
#define CH_TEMP	0x0006

		
//MODE register		
#define MD_CONTINUOUS_CONVERSION	0x0000
#define MD_SINGLE_CONVERSION		0x2000
#define MD_IDLE						0x4000
#define MD_POWERDOWN				0x6000
#define MD_INTERNAL_ZS_CALIBRATION	0x8000
#define MD_INTERNAL_FS_CALIBRATION	0xA000
#define MD_SYSTEM_ZS_CALIBRATION	0xC000
#define MD_SYSTEM_FS_CALIBRATION	0xE000

#define PSW_ENABLE	0x1000
#define PSW_DISABLE	0x0000

#define AMP_CM_ENABLE	0x0200
#define AMP_CM_DISABLE	0x0000

#define CLK_INTERNAL_OUTPUT_DISABLE	0x0000
#define CLK_INTERNAL_OUTPUT_ENABLE	0x0040
#define CLK_EXTERNAL_DIVIDE_DISABLE	0x0080
#define CLK_EXTERNAL_DIVIDE_ENABLE	0x00C0

#define CHOP_DISABLE	0x0000
#define CHOP_ENABLE		0x0010

#define FILTER_UPDATE_RATE_470	0x01
#define FILTER_UPDATE_RATE_242	0x02
#define FILTER_UPDATE_RATE_123	0x03
#define FILTER_UPDATE_RATE_62	0x04
#define FILTER_UPDATE_RATE_50	0x05
#define FILTER_UPDATE_RATE_39	0x06
#define FILTER_UPDATE_RATE_33	0x07
#define FILTER_60_UPDATE_RATE_19	0x08
#define FILTER_50_UPDATE_RATE_16	0x09
#define FILTER_6050_UPDATE_RATE_16	0x0A
#define FILTER_6050_UPDATE_RATE_12	0x0B
#define FILTER_6050_UPDATE_RATE_10	0x0C
#define FILTER_6050_UPDATE_RATE_8	0x0D
#define FILTER_6050_UPDATE_RATE_6	0x0E
#define FILTER_6050_UPDATE_RATE_4	0x0F

//CONFIGURATION register
#define VBIAS_DISABLE	0x0000
#define VBIAS_AIN1N		0x4000
#define VBIAS_AIN2N		0x8000
#define VBIAS_AIN3N		0xC000

#define BO_ENABLE		0x2000
#define BO_DISABLE		0x0000

#define UNIPOLAR		0x1000
#define BIPOLAR			0x0000

#define BOOST_ENABLE	0x0800
#define BOOST_DISABLE	0x0000

#define GAIN_1		0x0000
#define GAIN_2		0x0100
#define GAIN_4		0x0200
#define GAIN_8		0x0300
#define GAIN_16		0x0400
#define GAIN_32		0x0500
#define GAIN_64		0x0600
#define GAIN_128	0x0700

#define REFSEL_EXTERNAL_REF1	0x0000
#define REFSEL_EXTERNAL_REF2	0x0040
#define REFSEL_INTERNAL			0x0080

#define REF_DETECT_ENABLE		0x0020
#define REF_DETECT_DIASBLE		0x0000

#define BUF_ENABLE		0x0010
#define BUF_DISABLE		0x0000

#define CHANNEL_AIN1	0x0000
#define CHANNEL_AIN2	0x0001
#define CHANNEL_AIN3	0x0002
#define CHANNEL_AIN4	0x0003
#define CHANNEL_AIN5	0x0004
#define CHANNEL_AIN6	0x0005
#define CHANNEL_TEMP	0x0006
#define CHANNEL_AVDD	0x0007
#define CHANNEL_AIN1N	0x0008
	
//DATA register		
#define DATA_MASK		0x0FFF
	
//ID register:	default 0xXF		
		
//IO register
#define IO_ENABLE	0x40
#define IO_DISABLE	0x00

#define IO2_DATA_SET	0x20
#define IO2_DATA_CLR	0x00
#define IO1_DATA_SET	0x10
#define IO1_DATA_CLR	0x00

#define IEXC_DIR_11_22	0x00
#define IEXC_DIR_12_21	0x40
#define IEXC_DIR_11_21	0x80
#define IEXC_DIR_12_22	0xC0

#define IEXC_DISABLE	0x00
#define IEXC_10UA		0x01
#define IEXC_210UA		0x02
#define IEXC_1MA		0x03

/*��ȡ���ݵĴ�������������buf �Ĵ�С*/
#define AD7794_DATA_SAVE 10



/*(CONFIGURATION)���üĴ���ֵ*/
#define SFR_CFG_VBIAS_DISABLE				0x0000			/*ƫִ��ѹ����������*/
#define SFR_CFG_VBIAS_CONNECT_TO_AIN1		0x4000			/*ƫִ��ѹ���������ӵ�AIN1(-)*/
#define SFR_CFG_VBIAS_CONNECT_TO_AIN2		0x8000			/*ƫִ��ѹ���������ӵ�AIN2(-)*/
#define SFR_CFG_VBIAS_CONNECT_TO_AIN3		0xC000			/*ƫִ��ѹ���������ӵ�AIN3(-)*/

#define SFR_CFG_BO							0x0000			/*�۶ϵ���ʹ��λ�����뽫��λ�༭Ϊ0������������*/

#define SFR_CFG_UB_U						0x1000			/*������ģʽ*/							
#define SFR_CFG_UB_B						0x0000			/*˫����ģʽ*/			

#define SFR_CFG_BOOST_ENABLE				0x0800			/*��1ʱ��ƫ�õ�ѹ�������Ĺ�����ߣ��ϵ�ʱ������*/
#define SFR_CFG_BOOST_DISABLE				0x0000			/*��0*/

#define SFR_CFG_ADC_RANGE_2_5V				0x0000			/*ADC�����ѹ��Χ2.5 V */
#define SFR_CFG_ADC_RANGE_1_25V				0x0100			/*ADC�����ѹ��Χ1.25 V */
#define SFR_CFG_ADC_RANGE_625MV				0x0200			/*ADC�����ѹ��Χ625 mV  */
#define SFR_CFG_ADC_RANGE_312_5MV			0x0300			/*ADC�����ѹ��Χ312.5 mV */
#define SFR_CFG_ADC_RANGE_156_2MV			0x0400			/*ADC�����ѹ��Χ156.2 mV  */
#define SFR_CFG_ADC_RANGE_78_125MV			0x0500			/*ADC�����ѹ��Χ78.125 mV  */
#define SFR_CFG_ADC_RANGE_39_06MV			0x0600			/*ADC�����ѹ��Χ39.06 mV  */
#define SFR_CFG_ADC_RANGE_19_53MV			0x0700			/*ADC�����ѹ��Χ19.53 mV  */

#define SFR_CFG_REF_REFIN1_REFIN1			0x0000			/*��REFIN1(+)��REFIN1(-)֮��ʩ�ӵ��ⲿ��׼��ѹԴ*/
#define SFR_CFG_REF_REFIN1_REFIN2			0x0040			/*��REFIN1(+)��REFIN2(-)֮��ʩ�ӵ��ⲿ��׼��ѹԴ*/
#define SFR_CFG_REF_INTER_1_17V				0x0080			/*1.17V�ڲ���׼��ѹԴ*/

#define SFR_CFG_REF_DET_ENABLE				0x0020			/*���ADC�����ⲿ��׼��ѹԴ��·����С��0.5v��״̬�Ĵ����е�NOXREFλ��������ʾ*/
#define SFR_CFG_REF_DET_DISABLE				0x0000			/*��׼��ѹ��⹦�ܹر�*/

#define SFR_CFG_CHANNEL_AIN1				0x0000			/*AIN1 ������Ч*/
#define SFR_CFG_CHANNEL_AIN2				0x0001			/*AIN2 ������Ч*/
#define SFR_CFG_CHANNEL_AIN3				0x0002			/*AIN3 ������Ч*/
#define SFR_CFG_CHANNEL_AIN4				0x0003			/*AIN4 ������Ч*/
#define SFR_CFG_CHANNEL_AIN5				0x0004			/*AIN5 ������Ч*/
#define SFR_CFG_CHANNEL_AIN6				0x0005			/*AIN6 ������Ч*/
#define SFR_CFG_CHANNEL_TEMPERATURE			0x0006			/*�¶ȴ��������Զ�ѡ��1.17v�ڲ���׼��ѹ��������������Ϊ1*/
#define SFR_CFG_CHANNEL_VDD					0x0007			/*VDD ��أ��Զ�ѡ��1.17v�ڲ���׼��ѹ��������������Ϊ1/6*/


/*(MODE)ģʽ�Ĵ���*/
#define SFR_MODE_CONTINUOUS_CONVERSION		0x0000			/*����ת��*/
#define SFR_MODE_SINGLE_CONVERSION			0x2000			/*����ת��*/
#define SFR_MODE_IDLE						0x4000			/*����ģʽ*/
#define SFR_MODE_POWE_SAVE					0x6000			/*ʡ��ģʽ*/

#define SFR_MODE_PSW_ENABLE					0x1000			/*��Դ����PSW �պ���GND ,��Դ���ؿ��ṩ30ma ��������*/
#define SFR_MODE_PSW_DISABLE				0x0000			/*��Դ���ضϿ�����ADC ����ʡ��ģʽʱ����Դ���ضϿ�*/


#define SFR_MODE_APM_CM_ENABLE				0x0200			
#define SFR_MODE_APM_CM_DISABLE				0x0000			

#define SFR_MODE_INTER_64K_CRYSTAL_DIS_CLK	0x0000			/*64k �ڲ�ʱ�ӣ�CLK �Ų��ṩ�ڲ�ʱ��*/			
#define SFR_MODE_INTER_64K_CRYSTAL_EN_CLK	0x0040			/*64k �ڲ�ʱ�ӣ�CLK �Ų��ṩ�ڲ�ʱ��*/			
#define SFR_MODE_EXTERNAL_64K_CRYSTAL		0x0080			/*64k �ⲿʱ��*/			
#define SFR_MODE_EXTERNAL_64K_CRYSTAL_1_2	0x00C0			/*64k �ⲿʱ��, ����2 ��Ƶ*/

#define SFR_MODE_FADC_479HZ					0x0001
#define SFR_MODE_FADC_242HZ					0x0002
#define SFR_MODE_FADC_123HZ					0x0003
#define SFR_MODE_FADC_62HZ					0x0004
#define SFR_MODE_FADC_50HZ					0x0005
#define SFR_MODE_FADC_39HZ					0x0006
#define SFR_MODE_FADC_33_2HZ				0x0007
#define SFR_MODE_FADC_19_6HZ				0x0008
#define SFR_MODE_FADC_16_7H					0x0009
#define SFR_MODE_FADC_16_7HZ				0x000A
#define SFR_MODE_FADC_12_5HZ				0x000B
#define SFR_MODE_FADC_10HZ					0x000C
#define SFR_MODE_FADC_8_33HZ				0x000D
#define SFR_MODE_FADC_6_25HZ				0x000E
#define SFR_MODE_FADC_4_17HZ				0x000F




//OFFSET register	
		
//FULLSCALE register		

//----------------------
//Function declarations
//----------------------
void AD7794InitGpio(void);
void AD7794WriteRegister(unsigned char RegisterAddress, unsigned long int *RegisterBuffer);
void AD7794ReadRegister(unsigned char RegisterAddress, unsigned long int *RegisterBuffer);
void AD7794ReadResultForSingleConversion(unsigned char DataIndex, unsigned long int *DataBuffer);
void AD7794ReadResultForContinuousConversion(unsigned char StartIndex, unsigned char NumberOfData, unsigned long int *DataBuffer);
void AD7794ReadResultForContinuousRead(unsigned char StartIndex, unsigned char NumberOfData, unsigned long int *DataBuffer);
void AD7794ExitContinuousRead(void);
void AD7794SoftwareReset(void);
void AD7794GetAllAdcData(unsigned long int channel,unsigned count);


#endif
