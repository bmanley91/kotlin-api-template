package dev.manley.kotlinapitemplate.usecase.person

import dev.manley.kotlinapitemplate.domain.exception.EmailAlreadyExistsException
import dev.manley.kotlinapitemplate.domain.model.Person
import dev.manley.kotlinapitemplate.domain.repository.PersonRepository
import org.springframework.stereotype.Component

@Component
class CreatePersonUsecase (
    private val personRepository: PersonRepository,
//    private val passwordEncoder: PasswordEncoder
) {
    fun execute(person: Person): Person {
        personRepository.findByEmail(person.email)?.let {
            throw EmailAlreadyExistsException("Email ${person.email} already in use.")
        }

        // TODO: Implement password hash
        // val hashedPassword = passwordEncoder.encode(person.password)
        // val newPerson = person.copy(password = hashedPassword)

        return personRepository.save(person)
    }
}