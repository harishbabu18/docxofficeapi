package docxofficeapi

import grails.gorm.services.Service

@Service(Documents)
interface DocumentsService {

    Documents get(Serializable id)

    List<Documents> list(Map args)

    Long count()

    Documents delete(Serializable id)

    Documents save(Documents documents)

}
