package pkg;

import java.math.*;
import java.security.*;

@SuppressWarnings("unused")
public class RSA {

	// verificar se as chaves podem ser publicas
	static BigInteger n, d, e;

	public RSA(int bitlen) throws Exception {
		try {
			// Escolha de forma aleatória dois números primos grandes p e q
			SecureRandom r = new SecureRandom();
			BigInteger p = new BigInteger(bitlen / 2, 100, r);
			BigInteger q = new BigInteger(bitlen / 2, 100, r);

			// Calcula n = p * q
			n = p.multiply(q);

			// Calcular função totiene, phi(n) = (p -1) (q -1)
			BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

			// Escolher um inteiro `e`, que satisfaça 1 < `e` < m, `e` e phi(n) devem ser
			// primos
			// entre si.
			e = new BigInteger("3");
			while (m.gcd(e).intValue() > 1)
				e = e.add(new BigInteger("2"));

			// `d` deve ser inverso multiplicativo de `e`
			d = e.modInverse(m);

			System.out.println("p: " + p);
			System.out.println("q: " + q);
			System.out.println("n: " + n);
			System.out.println("e: " + e);
			System.out.println("d: " + d);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		// https://en.wikipedia.org/wiki/RSA_(cryptosystem)
		String text = "Teste";
		int bitlen = 2048;

		new RSA(bitlen);

		// mensagem original
		System.out.println("\nMensagem original:\t" + text);
		// mensagem cifrada
		System.out.println("Mensagem cifrada:\t" + RSA_encrypt(text, e, n));

		// mensagem decifrada
		System.out.println("Mensagem decifrada:\t" + RSA_decrypt(RSA_encrypt(text, e, n), d, n));
	}

	private static String RSA_encrypt(String text, BigInteger e2, BigInteger n2) {
		String textcifrado;
		return textcifrado = new BigInteger(text.getBytes()).modPow(e, n).toString();
	}

	private static String RSA_decrypt(String text, BigInteger e2, BigInteger n2) {
		String textdecifrado;
		return textdecifrado = new String(new BigInteger(text).modPow(d, n).toByteArray());
	}

}
