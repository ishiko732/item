library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
entity mux41_clk is
port(
	R0,R1,R2,R3:in std_logic_vector(7 downto 0);
				 S:in std_logic_vector(1 downto 0);
				 R:out std_logic_vector(7 downto 0)
);
end mux41_clk;

architecture bhv of mux41_clk is
begin
	 process(S)
	 begin
		case S is
			when "00"=>R<=R0;
			when "01"=>R<=R1;
			when "10"=>R<=R2;
			when "11"=>R<=R3;
		end case;
	end process;
end bhv;
		