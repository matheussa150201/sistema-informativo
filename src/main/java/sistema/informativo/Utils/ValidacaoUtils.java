package sistema.informativo.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import sistema.informativo.exception.ValidacaoException;

@Component
public class ValidacaoUtils {

    @Autowired
    private final MessageSource messageSource;

    public ValidacaoUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void validarInformacoes(String valor, String nomeCampo) {
        validarNotNullNotEmpty(valor, nomeCampo);
    }

    public void validarInformacoes(Long valor, String nomeCampo) {
        validarNotNull(valor, nomeCampo);
    }

    /**
     * Valida se uma String não é nula ou vazia.
     *
     * @param valor      A String a ser validada.
     * @param nomeCampo  O nome do campo associado à String.
     * @throws ValidacaoException Se a String for nula ou vazia.
     */
    private void validarNotNullNotEmpty(String valor,  String nomeCampo) {
        if (valor == null || StringUtils.isBlank(valor)) {
            String mensagem = messageSource.getMessage("validation.error.invalido", new Object[]{nomeCampo}, null);
            throw new ValidacaoException(mensagem);
        }
    }

    /**
     * Valida se um valor Long não é nulo.
     *
     * @param valor      O valor Long a ser validado.
     * @param nomeCampo  O nome do campo associado ao valor Long.
     * @throws ValidacaoException Se o valor Long for nulo.
     */
    private void validarNotNull(Long valor,  String nomeCampo) {
        if (valor == null ) {
            String mensagem = messageSource.getMessage("validation.error.invalido", new Object[]{nomeCampo}, null);
            throw new ValidacaoException(mensagem);
        }
    }

}
