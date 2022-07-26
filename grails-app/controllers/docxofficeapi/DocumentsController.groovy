package docxofficeapi

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class DocumentsController {

    DocumentsService documentsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond documentsService.list(params), model:[documentsCount: documentsService.count()]
    }

    def show(Long id) {
        respond documentsService.get(id)
    }

    @Transactional
    def save(Documents documents) {
        if (documents == null) {
            render status: NOT_FOUND
            return
        }
        if (documents.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond documents.errors
            return
        }

        try {
            documentsService.save(documents)
        } catch (ValidationException e) {
            respond documents.errors
            return
        }

        respond documents, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Documents documents) {
        if (documents == null) {
            render status: NOT_FOUND
            return
        }
        if (documents.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond documents.errors
            return
        }

        try {
            documentsService.save(documents)
        } catch (ValidationException e) {
            respond documents.errors
            return
        }

        respond documents, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || documentsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
