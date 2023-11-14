package playground;

import java.util.HashSet;
import java.util.Objects;

class Demonstration {

    public static class SpecialPerson {

        String fullName = init();
        String name = "batman";

        public SpecialPerson() {
            name = "superMan";
        }

        private String init() {
            return name;
        }

        public void print() {
            System.out.println(fullName);
        }
    }


    @SuppressWarnings("unchecked")
    public static void main( String args[] ) {

        SpecialPerson specialPerson = new SpecialPerson();
        specialPerson.print();


        HashSet<Celebrity> set = new HashSet();
        Celebrity realKardashian = new Celebrity("Kim", 17);
        Celebrity kardashianClone = new Celebrity("Kim", 17);
        set.add(realKardashian);

        if (set.contains(kardashianClone)) {
            System.out.println("Kim is a celebrity");
        } else {
            System.out.println("Can't find Kim");
        }

        System.out.println(realKardashian.equals(kardashianClone));
        System.out.println(realKardashian.hashCode() + " " +kardashianClone.hashCode());
    }
}

class Celebrity {

    String name;
    int age;

    public Celebrity(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Override
    public int hashCode() {
        System.out.println(Objects.hash(name,age));
        return Objects.hash(name, age);
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Celebrity) || obj == null)
            return false;

        Celebrity otherCeleb = (Celebrity) obj;
        return name.equals(otherCeleb.name);
    }

}
