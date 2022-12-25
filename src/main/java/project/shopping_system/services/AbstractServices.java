package project.shopping_system.services;

import java.util.List;

public interface AbstractServices<T>  {

     void add(T newObject);
     void edit(T Object);
     void remove(long id);
     T findObject(long id);
     boolean isExistObject(long id);
     List<T> sortByNameAToZ();
     List<T> sortByNameZToA();
}
