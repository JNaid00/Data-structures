#include "Node.h"
#include <cstddef>
#include <iostream>


template <class T>
Node<T>::Node(T val)
{
    this->next = NULL;
    this->prev = NULL;
    this->value = new T(val);
}

template <class T>
Node<T>::~Node()
{
    delete this->value;    
}