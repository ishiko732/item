import pandas as pd
import os
from openpyxl import Workbook


def getExcel(filepath):  # 导入表格信息，返回list，每项类型为dict
    sheet = pd.read_excel(filepath, sheet_name=None)
    year = {}
    for j1 in sheet.values():
        j1 = j1.to_dict(orient='records')
    for tmp in j1:
        # year[tmp['学年']]=year.get(tmp['学年'], list())
        if tmp['学年'] not in year.keys():
            year[tmp['学年']] = list()
        else:
            year[tmp['学年']].append(tmp)
    return year


def getGPA(y, msg: list):  # 获取某学年的绩点
    # print(msg)
    text = ''
    try:
        sno = msg[0]['学号']
    except:
        sno = '未获取'
    try:
        sname = msg[0]['姓名']
    except:
        sname = '未获取'
    try:
        credits, dot = 0, 0.0
        for d in range(len(msg)):
            if msg[d]['成绩'] == '缓考':  # 排除缓考信息
                text += '{}(缓考) '.format(msg[d]['课程名称'])
                continue
            if msg[d]['成绩性质'] == '补考':  # 排除缓考信息
                print(msg[d]['学期'], msg[d]['课程名称'], float(msg[d]['绩点']))
                if msg[d]['学期'] == 1:  # and float(d['绩点']) < 1:  # 补考剔除上一学年信息
                    continue
                elif msg[d]['学期'] == 2 and float(msg[d]['绩点']) > 0.95:  # 如果是第二个学期，应该覆盖第一个学期的成绩
                    for i in range(d, len(msg)):
                        if msg[i]['课程代码'] == msg[d]['课程代码']:
                            msg[i]['绩点'] = msg[d]['绩点']
                            msg[i]['成绩'] = msg[d]['成绩']
                            print('补考修正第一学期成绩')
                            break
                    continue
            elif msg[d]['绩点']<0.95:
                text += '{}(挂科{}) '.format(msg[d]['课程名称'],msg[d]['成绩'])
            # 绩点计算 累乘相加/对应总学分
            credits += float(msg[d]['学分'])
            dot += float(msg[d]['学分']) * float(msg[d]['绩点'])
        GPA = dot / credits
    except:
        GPA = -1.0
        credits = -1.0
    print('学号：{} 姓名：{} {}学年绩点：{:.2f}'.format(sno, sname, y, GPA))
    return {'name': sname,
            'sno': sno,
            'year': y,
            'credit': credits,
            'GPA': GPA,
            'msg': text
            }


dir = u'/Volumes/MW/item/xlsxtoGPA'  # 表格文件夹 default
tmp = input('请输入成绩信息的文件夹：')
if tmp != '':  # 若未输入
    dir = tmp

mybook = Workbook()  # 创建输出对象
wa = mybook.active  # 激活对象
wa.append(['学号', '姓名', '学年', '该学年学分', '绩点', '文件名', '备注'])  # 表头
for (dirpath, dirnames, filenames) in os.walk(dir):  # 遍历目录寻找表格文件
    for filename in filenames:
        ext = os.path.splitext(filename)[1]
        fname = os.path.split(filename)[1]
        if ext in ['.xls', '.xlsx'] and filename[0:2] not in ['._', '~$']:  # 排除当前打开，macos资源
            print("文件信息 \'{}\':".format(fname))
            for k, v in getExcel(filename).items():
                get = getGPA(k, v)
                if get['GPA'] == -1:
                    continue
                wa.append(
                    [str(get['sno']), get['name'], get['year'], get['credit'], '{:.2f}'.format(get['GPA']), filename,
                     get['msg']])
                # 导出数据到表格
            print()

print(mybook.save(os.path.join(os.path.expanduser("~"), 'Desktop', 'test.xlsx')))  # 写出表格文件到桌面
