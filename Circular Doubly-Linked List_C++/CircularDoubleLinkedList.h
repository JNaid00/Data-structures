#ifndef CIRCULARDOUBLELINKEDLIST_H
#define CIRCULARDOUBLELINKEDLIST_H
#include <cstddef>
#include <iostream>
#include "Node.h"


template <class T>
class DynamicList;

template <class T>
std::ostream& operator<<(std::ostream& output,const CircularDoubleLinkedList<T>& list);


template <class T>
class CircularDoubleLinkedList
{
    private:
        Node<T> *head;
    public:
        CircularDoubleLinkedList();
        ~CircularDoubleLinkedList();
        Node<T>* getHeadNode();
        int getSize() const;
        void operator+=(const T val);
        void operator+=(const CircularDoubleLinkedList<T>& list);
        T operator[](const int index);
        bool insertNodeAt(const int index, const T val);
        bool deleteNode(T val);
        bool operator==(const CircularDoubleLinkedList<T>& list);
        friend std::ostream& operator<< <T>(std::ostream& output,const CircularDoubleLinkedList<T>& list);

};
#endif
