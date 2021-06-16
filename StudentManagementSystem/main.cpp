#include "Student.h"
#include "studentMessage.h"

using namespace std;

void showMenu();

int main() {
    int h;
    studentMessage stuM = studentMessage();
    while (1) {
        showMenu();
        cout <<"请输入:";
        cout << "请输入操作对应的序号 : ";
        cin >> h;
        cout << endl;
        switch (h) {
            case 1:
                stuM.showAll();
                break;
            case 2:
                stuM.add();
                break;
            case 3:
                stuM.searchId();
                break;
            case 4:
                stuM.searchName();
                break;
            case 5:
                stuM.del();
                break;
            case 6:
                stuM.clear();
                return 0;
            case 7:
                stuM.change();
                break;
            case 8:
                stuM.read();
                break;
            case 0:
                cout << "\n退出成功！";
                return 0;
            default:
                cout << "\n输入序号有误！请输入正确的序号。" << endl;
        }
    }
}

void showMenu() {
    cout << "                               \n";
    cout << "===============================\n";
    cout << "      学生成绩管理系统\n\n";
    cout << "      1.显示所有学生成绩\n";
    cout << "      2.添加信息\n";
    cout << "      3.学号查询信息\n";
    cout << "      4.名字查询信息\n";
    cout << "      5.删除信息\n";
    cout << "      6.清空所有信息并退出系统\n";
    cout << "      7.修改信息\n";
    cout << "      8.读取文件\n";
    cout << "      0.退出系统\n";
    cout << "===============================\n";
    cout << "                               \n";
}