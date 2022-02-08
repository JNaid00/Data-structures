#ifndef NODE_H
#define NODE_H
#include <cstddef>
#include <iostream>

template <class T>
class Node
{
    public:
        Node<T> *next;
        Node<T> *prev;
        T *value;
        Node(T val);
        ~Node();
};
#endif