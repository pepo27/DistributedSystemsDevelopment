import exercises.EX1.db.tools.DatabaseQueriesTrait
import exercises.EX1.entities.Career
import exercises.EX1.entities.Student
import spock.lang.Shared

/**
 * Created by alberto on 4/11/15.
 *
 Integer id
 String name
 String LastName
 Date birthDate
 Integer career
 String email
 */
class DatabaseQueriesSpec extends spock.lang.Specification {
    @Shared
    DatabaseQueriesTrait testInstance = new Object() as DatabaseQueriesTrait

    def "Should return a simple query from a given bean"() {
        expect:
        testInstance.getAll(entity) == result
        where:
        entity                                     | result
        new Student([id: 1, name: 'pepo'])         | "select (birthDate,career,email,id,lastName,name) from Student"
        new Career([id: 1, description: 'Doctor']) | "select (description,id) from Career"
    }

    def "Should return an specific projection query from a given bean"() {
        expect:
        testInstance.getAll(entity,filter) == result
        where:
        entity                                     |filter  || result
        new Student([id: 1, name: 'pepo'])         |['id']  || "select (id) from Student"
        new Career([id: 1, description: 'Doctor']) |['description'] || "select (description) from Career"
        new Career([id: 1, description: 'Doctor']) |[]  || "select (description,id) from Career"
    }

    def "Should return an specific query from a given bean using given some conditions"() {
        expect:
        testInstance.findBy(entity,filter,conditions) == result
        where:
        entity                                     |filter          | conditions            || result
        new Student([id: 1, name: 'pepo'])         |['id']          |[id:20]                || "select (id) from Student where id=20"
        new Career([id: 1, description: 'Doctor']) |['description'] |[description: '%Des%'] || "select (description) from Career where description like '%Des%'"
    }
}
