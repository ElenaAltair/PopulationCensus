import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        //Найти количество несовершеннолетних (т.е. людей младше 18 лет)
        System.out.println(persons.stream()
                .filter(p -> p.getAge() < 18)
                .count()
        );


        //Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет)
        //Stream<Person> conscripts = persons.stream();
        System.out.println(persons.stream()
                .filter(p -> p.getAge() < 27)
                .filter(p -> p.getAge() >= 18)
                .map(p -> p.getFamily())
                .collect(Collectors.toList())
        );


        //Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
        // (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин)
        System.out.println(persons.stream()
                .filter(p -> p.getEducation().toString().equals("HIGHER"))
                .filter(p -> (p.getSex().toString().equals("MAN")) ? p.getAge() < 65 && p.getAge() > 18 : p.getAge() < 60 && p.getAge() > 18)
                .sorted(new familyComparator())
                .collect(Collectors.toList())
        );


    }
}

class familyComparator implements Comparator<Person> {
    public int compare(Person a, Person b) {
        return a.getFamily().toUpperCase().compareTo(b.getFamily().toUpperCase());
    }
}
