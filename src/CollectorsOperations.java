import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsOperations {
    public static void main(String[] args) {

        //****** Joining ******//
        String joining = Stream.of("E", "F", "G", "H").collect(Collectors.joining());
        System.out.println(joining);   //     EFGH

        String joiningWithDelimiter = Stream.of("E", "F", "G", "H").collect(Collectors.joining(","));
        System.out.println(joiningWithDelimiter);   //     E,F,G,H

        String joiningWithDelimiterPrefixAndSuffix = Stream.of("E", "F", "G", "H").collect(Collectors.joining(",", "{", "}"));
        System.out.println(joiningWithDelimiterPrefixAndSuffix);   // {E,F,G,H}


        //****** counting ******//
        long count = Stream.of(1, 2, 3, 4, 5, 6, 7).collect(Collectors.counting());
        System.out.println(count);// 7

        long countWithCollectors = Stream.of(1, 2, 3, 4, 5, 6, 7).count();
        System.out.println(countWithCollectors);// 7

        // count the number of instructors who teaches online courses

        Long getInstructorsWithOnlineCourses = Instructors.getAll()
                .stream().filter(Instructor::isOnlineCourses)
                .count();

        System.out.println(getInstructorsWithOnlineCourses); // 3

        Long getInstructorsWithOnlineCoursesWithCollectors = Instructors.getAll()
                .stream().filter(Instructor::isOnlineCourses)
                .collect(Collectors.counting());

        System.out.println(getInstructorsWithOnlineCourses); // 3

        //counting as downstream operation
        Map<String, Long> groupInstructorByNameCount = Instructors.getAll().stream()
                .filter(Instructor::isOnlineCourses)
                .collect(Collectors.groupingBy(
                        Instructor::getName, Collectors.counting()
                ));
        System.out.println(groupInstructorByNameCount); // {Syed=1, Mike=1, Anthony=1}


        //****** Mapping ******//

        List<String> instructorNameList = Instructors.getAll().stream().collect(Collectors.mapping(Instructor::getName, Collectors.toList()));
        System.out.println(instructorNameList);  //[Mike, Jenny, Gene, Anthony, Syed]

        List<String> instructorNameListWithoutCollectors = Instructors.getAll().stream()
                .map(Instructor::getName)
                .collect(Collectors.toList());
        System.out.println(instructorNameListWithoutCollectors);// [Mike, Jenny, Gene, Anthony, Syed]

        //mapping as downstream operation
        Map<Integer, List<String>> instructorNameListWithGroupBy = Instructors.getAll().stream()
                .collect(Collectors.groupingBy(
                        Instructor::getYearsOfExperience, Collectors.mapping(Instructor::getName, Collectors.toList())
                ));
        System.out.println(instructorNameListWithGroupBy);// {5=[Jenny], 6=[Gene], 10=[Mike], 15=[Anthony, Syed]}


        //****** minBy ******//

        Optional<Instructor> minYearOfExpInstructorOptionalUsingCollector = Instructors.getAll().stream()
                .collect(Collectors.minBy(Comparator.comparing(Instructor::getYearsOfExperience)));
        minYearOfExpInstructorOptionalUsingCollector.ifPresent(instructor -> System.out.println(instructor));
        //Instructor{name='Jenny', yearsOfExperience=5, title='Engineer', gender='F', onlineCourses=false, courses=[Multi-Threaded Programming, CI/CD, Unit Testing]}

        Optional<Instructor> minYearOfExpInstructorOptional = Instructors.getAll().stream().min(Comparator.comparing(Instructor::getYearsOfExperience));
        minYearOfExpInstructorOptional.ifPresent(instructor -> System.out.println(instructor));
        //Instructor{name='Jenny', yearsOfExperience=5, title='Engineer', gender='F', onlineCourses=false, courses=[Multi-Threaded Programming, CI/CD, Unit Testing]}

        Optional<Instructor> maxYearOfExpInstructorOptional = Instructors.getAll().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Instructor::getYearsOfExperience)));
        maxYearOfExpInstructorOptional.ifPresent(instructor -> System.out.println(instructor));
        //Instructor{name='Anthony', yearsOfExperience=15, title='Senior Developer', gender='M', onlineCourses=true, courses=[Java Programming, Angular Programming, React Native]}


        //****** summingInt ******//
        Integer sum = Stream.of(1, 2, 3).collect(Collectors.summingInt(p -> p));
        System.out.println(sum); // 6

        //****** averagingInt ******//
        Double avg = Stream.of(1, 2, 3).collect(Collectors.averagingInt(p -> p));
        System.out.println(avg); // 2.0

        //****** groupingBy ******//

        /***** classifier ****/
        Map<String, List<Instructor>> groupByName = Instructors.getAll().stream().collect(Collectors.groupingBy(Instructor::getName));
        System.out.println(groupByName);

        /*{Syed=[Instructor{name='Syed', yearsOfExperience=15, title='Principal Engineer', gender='M', onlineCourses=true, courses=[Java Programming, Java Multi-Threaded Programming, React Native]}],
         Mike=[Instructor{name='Mike', yearsOfExperience=10, title='Software Developer', gender='M', onlineCourses=true, courses=[Java Programming, C++ Programming, Python Programming]}],
          Anthony=[Instructor{name='Anthony', yearsOfExperience=15, title='Senior Developer', gender='M', onlineCourses=true, courses=[Java Programming, Angular Programming, React Native]}],
           Gene=[Instructor{name='Gene', yearsOfExperience=6, title='Manager', gender='M', onlineCourses=false, courses=[C++ Programming, C Programming, React Native]}],
            Jenny=[Instructor{name='Jenny', yearsOfExperience=5, title='Engineer', gender='F', onlineCourses=false, courses=[Multi-Threaded Programming, CI/CD, Unit Testing]}]} */

        Map<String, List<Instructor>> groupByYearsOfExp = Instructors.getAll().stream()
                .collect(Collectors.groupingBy(instructor -> instructor.yearsOfExperience > 10 ? "senior" : "junior"));
        System.out.println(groupByYearsOfExp);

        /***** classifier + downstream ****/
        List<String> names = Arrays.asList("Sid", "Mike", "Jenny");
        Map<Integer, List<String>> groupByNameLength = names.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.filtering(s -> s.contains("S"), Collectors.toList())));
        System.out.println(groupByNameLength);
        // {3=[Sid], 4=[], 5=[]}

        Map<String, List<Instructor>> groupInstructorBasedOnExpAndFilterBasedOnCourses = Instructors.getAll().stream()
                .collect(Collectors.groupingBy(instructor -> instructor.getYearsOfExperience() > 10 ? "senior" : "junior",
                        Collectors.filtering(Instructor::isOnlineCourses, Collectors.toList())));
        System.out.println(groupInstructorBasedOnExpAndFilterBasedOnCourses);
        //{senior=[Instructor{name='Anthony', yearsOfExperience=15, title='Senior Developer', gender='M', onlineCourses=true, courses=[Java Programming, Angular Programming, React Native]}, Instructor{name='Syed', yearsOfExperience=15, title='Principal Engineer', gender='M', onlineCourses=true, courses=[Java Programming, Java Multi-Threaded Programming, React Native]}], junior=[Instructor{name='Mike', yearsOfExperience=10, title='Software Developer', gender='M', onlineCourses=true, courses=[Java Programming, C++ Programming, Python Programming]}]}

        /***** classifier + mapfactory +  downstream ****/
        Map<Integer, List<String>> group = names.stream()
                .collect(Collectors.groupingBy(String::length, LinkedHashMap::new,
                        Collectors.filtering(s -> s.contains("S"), Collectors.toList())));
        System.out.println(groupByNameLength);
        //{3=[Sid], 4=[], 5=[]}

        Map<Boolean, Optional<Instructor>> groupInstructorWithOnlineCourcesHavingMaxYearOfExp = Instructors.getAll().stream().collect(Collectors.groupingBy(Instructor::isOnlineCourses,
                Collectors.maxBy(Comparator.comparing(Instructor::getYearsOfExperience))));
        System.out.println(groupInstructorWithOnlineCourcesHavingMaxYearOfExp);
//{false=Optional[Instructor{name='Gene', yearsOfExperience=6, title='Manager', gender='M', onlineCourses=false, courses=[C++ Programming, C Programming, React Native]}], true=Optional[Instructor{name='Anthony', yearsOfExperience=15, title='Senior Developer', gender='M', onlineCourses=true, courses=[Java Programming, Angular Programming, React Native]}]}

        Map<Boolean, Instructor> groupInstructorWithOnlineCourcesHavingMaxYearOfEx = Instructors.getAll().stream().collect(Collectors.groupingBy(Instructor::isOnlineCourses,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Instructor::getYearsOfExperience)),Optional::get)));
        System.out.println(groupInstructorWithOnlineCourcesHavingMaxYearOfEx);
        //{false=Instructor{name='Gene', yearsOfExperience=6, title='Manager', gender='M', onlineCourses=false, courses=[C++ Programming, C Programming, React Native]}, true=Instructor{name='Anthony', yearsOfExperience=15, title='Senior Developer', gender='M', onlineCourses=true, courses=[Java Programming, Angular Programming, React Native]}}


    }
}
